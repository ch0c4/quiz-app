package org.johan.application.useCases.answeredQuestion;

import org.johan.application.exceptions.answeredQuestion.AnsweredQuestionBadRequestException;
import org.johan.domain.quizzes.exception.AnsweredQuestionNotFoundException;
import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.Customer;
import org.johan.domain.customers.ICustomerRepository;
import org.johan.domain.quizzes.IQuizRepository;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.domain.quizzes.valueObjects.QuestionId;
import org.johan.domain.quizzes.valueObjects.QuizId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;

@Singleton
public class AnsweredQuestionUseCase {

    @Inject
    private ICustomerRepository customerRepository;

    @Inject
    private IQuizRepository quizRepository;

    private OutputPort outputPort;

    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void execute(AnsweredQuestionInput input) throws AnsweredQuestionBadRequestException, AnsweredQuestionNotFoundException {
        if (input.getModelState().isValid()) {
            answeredQuestionInternal(input.getCustomerId(), input.getQuizId(), input.getQuestionId(), input.getAnswer(), input.getTimeFinished());
        } else {
            this.outputPort.invalid(input.getModelState());
        }
    }

    private void answeredQuestionInternal(CustomerId customerId, QuizId quizId, QuestionId questionId, Answer answer, Timestamp timeFinished) throws AnsweredQuestionNotFoundException {
        Customer existingCustomer = customerRepository.find(customerId);

        if (existingCustomer == null) {
            this.outputPort.notFound();
            return;
        }

        Quiz existingQuiz = quizRepository.findQuiz(quizId);

        if (existingQuiz == null) {
            this.outputPort.notFound();
            return;
        }

        Question existingQuestion = existingQuiz.getQuestion(questionId);

        if (existingQuestion == null) {
            this.outputPort.notFound();
            return;
        }

        if (existingQuestion.getCorrectAnswer().equals(answer)) {
            existingQuestion.answeredCorrectly();
        } else {
            existingQuestion.answeredWrongly();
        }

        existingQuiz.getQuestions().update(existingQuestion);
        Question nextQuestion = existingQuiz.getNextQuestion();

        if (nextQuestion == null) {
            existingQuiz.isCompleted(timeFinished);
            this.quizRepository.update(existingQuiz);
            this.outputPort.finishQuiz();
            return;
        }

        this.quizRepository.update(existingQuiz);
        this.outputPort.ok(nextQuestion);
    }
}
