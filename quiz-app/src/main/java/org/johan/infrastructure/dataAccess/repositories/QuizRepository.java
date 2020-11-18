package org.johan.infrastructure.dataAccess.repositories;

import org.johan.domain.quizzes.exception.AnsweredQuestionNotFoundException;
import org.johan.domain.common.CustomerId;
import org.johan.domain.quizzes.IQuizRepository;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.QuestionCollection;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.domain.quizzes.questions.answers.AnswerCollection;
import org.johan.domain.quizzes.valueObjects.*;
import org.johan.infrastructure.dataAccess.entities.QuestionEntity;
import org.johan.infrastructure.dataAccess.entities.QuizEntity;
import org.johan.infrastructure.dataAccess.hibernate.QuestionHibernateRepository;
import org.johan.infrastructure.dataAccess.hibernate.QuizHibernateRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class QuizRepository implements IQuizRepository {

    @Inject
    private QuizHibernateRepository quizRepository;

    @Inject
    private QuestionHibernateRepository questionRepository;

    @Override
    public Quiz findQuiz(QuizId quizId) {
        Optional<QuizEntity> optionalQuizEntity = this.quizRepository.findById(quizId.getId());
        return optionalQuizEntity.map(QuizRepository::mapQuiz).orElse(null);
    }

    @Override
    public void update(Quiz quiz) throws AnsweredQuestionNotFoundException {
        Optional<QuizEntity> optionalQuizEntity = this.quizRepository.findById(quiz.getQuizId().getId());
        QuizEntity quizToUpdate = optionalQuizEntity.orElseThrow(AnsweredQuestionNotFoundException::new);

        QuizEntity updatedQuiz = mapQuiz(quiz);
        Set<QuestionEntity> questionEntitiesUpdated = new HashSet<>();
        for (QuestionEntity updatedQuestion : updatedQuiz.getQuestions()) {
            questionEntitiesUpdated.add(updateQuestion(updatedQuestion));
        }

        quizToUpdate.setCompleted(updatedQuiz.getCompleted());
        quizToUpdate.setTimeFinished(updatedQuiz.getTimeFinished());
        quizToUpdate.setQuestions(questionEntitiesUpdated);

        this.quizRepository.update(quizToUpdate);
    }

    private QuestionEntity updateQuestion(QuestionEntity updatedQuestion) throws AnsweredQuestionNotFoundException {
        Optional<QuestionEntity> optionalQuestionEntity = this.questionRepository.findById(updatedQuestion.getId());
        QuestionEntity questionToUpdate = optionalQuestionEntity.orElseThrow(AnsweredQuestionNotFoundException::new);

        questionToUpdate.setStatus(updatedQuestion.getStatus());

        return this.questionRepository.update(questionToUpdate);
    }

    private static QuizEntity mapQuiz(Quiz quiz) {

        Set<QuestionEntity> questionEntities = quiz.getQuestions().getQuestions()
                .stream()
                .map(QuizRepository::mapQuestion)
                .collect(Collectors.toSet());

        return QuizEntity.builder()
                .id(quiz.getQuizId().getId())
                .completed(quiz.getCompleted())
                .timeFinished(quiz.getTimeFinished().getTime())
                .questions(questionEntities)
                .build();
    }

    private static Quiz mapQuiz(QuizEntity quizEntity) {
        List<Question> questions = quizEntity.getQuestions()
                .stream()
                .map(QuizRepository::mapQuestion)
                .collect(Collectors.toList());

        return new Quiz(
                new QuizId(quizEntity.getId()),
                new QuestionCollection(questions),
                quizEntity.getCompleted(),
                new Timestamp(quizEntity.getTimeFinished()),
                new CustomerId(quizEntity.getCustomer().getId()));
    }

    private static Question mapQuestion(QuestionEntity questionEntity) {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(questionEntity.getCorrectAnswer(), true));
        answers.addAll(
                questionEntity.getIncorrectAnswer().stream()
                        .map(answer -> new Answer(answer, false)).collect(Collectors.toSet()));

        return new Question(
                new QuestionId(questionEntity.getId()),
                Category.parse(questionEntity.getCategory()),
                Difficulty.valueOf(questionEntity.getDifficulty().toUpperCase()),
                new Subject(questionEntity.getQuestion()),
                QuestionStatus.valueOf(questionEntity.getStatus().toUpperCase()),
                new AnswerCollection(answers));
    }

    private static QuestionEntity mapQuestion(Question question) {
        Set<String> incorrectAnswers = question.getIncorrectAnswer().stream().map(Answer::getText).collect(Collectors.toSet());
        return QuestionEntity.builder()
                .id(question.getQuestionId().getId())
                .category(question.getCategory().getText())
                .difficulty(question.getDifficulty().getText())
                .question(question.getSubject().getText())
                .status(question.getQuestionStatus().toString())
                .correctAnswer(question.getCorrectAnswer().getText())
                .incorrectAnswer(incorrectAnswers)
                .build();
    }


}
