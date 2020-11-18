import { SIGNUP, LOGIN, FETCH_QUIZZES, START_QUIZ, ANSWER_QUESTION, FINISH_QUIZ } from './Action';

export default (state, action) => {
  switch (action.type) {
    case SIGNUP:
      return { ...state, customer: action.payload };
    case LOGIN:
      return { ...state, customer: action.payload };
    case FETCH_QUIZZES:
      return { ...state, quizzes: action.payload };
    case START_QUIZ:
      return { ...state, currentQuiz: action.payload };
    case ANSWER_QUESTION:
      return { ...state, currentQuiz: action.payload };
    case FINISH_QUIZ:
      return { ...state, finishQuiz: action.payload };
    default:
      return state;
  }
};
