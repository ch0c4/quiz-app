export const SIGNUP = 'SIGNUP';
export const LOGIN = 'LOGIN';
export const FETCH_QUIZZES = 'FETCH_QUIZZES';
export const START_QUIZ = 'START_QUIZ';
export const ANSWER_QUESTION = 'ANSWER_QUESTION';
export const FINISH_QUIZ = 'FINISH_QUIZ';

export function signUpAction({ id, email, connected }) {
  return {
    type: SIGNUP,
    payload: { id, email, connected },
  };
}

export function loginAction({ id, email, connected }) {
  return {
    type: LOGIN,
    payload: { id, email, connected },
  };
}

export function fetchQuizzesAction(quizzes) {
  return {
    type: FETCH_QUIZZES,
    payload: quizzes,
  };
}

export function startQuizAction(quiz) {
  return {
    type: START_QUIZ,
    payload: quiz,
  };
}

export function answerQuestionAction(quiz) {
  return {
    type: ANSWER_QUESTION,
    payload: quiz,
  };
}

export function finishQuizAction(finishQuiz) {
  return {
    type: FINISH_QUIZ,
    payload: finishQuiz,
  };
}
