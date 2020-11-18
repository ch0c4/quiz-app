package org.johan.domain.quizzes;

import org.johan.domain.quizzes.exception.AnsweredQuestionNotFoundException;
import org.johan.domain.quizzes.valueObjects.QuizId;

public interface IQuizRepository {

    Quiz findQuiz(QuizId quizId);

    void update(Quiz quiz) throws AnsweredQuestionNotFoundException;
}
