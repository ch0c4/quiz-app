import React from 'react';
import ReactDOM from 'react-dom';
import { Switch, Route, BrowserRouter } from 'react-router-dom';
import LoginPage from './container/LoginPage';
import SignUpPage from './container/SignUpPage';
import QuizPage from './container/QuizPage';
import Dashboard from './container/Dashboard';
import { GlobalProvider } from './context/GlobalState';
import 'semantic-ui-css/semantic.min.css';
import { PrivateRoute } from './route/PrivateRoute';
import FinishQuizPage from './container/FinishQuizPage';

ReactDOM.render(
  <GlobalProvider>
    <BrowserRouter>
      <Switch>
        <Route path="/login" component={LoginPage} />
        <Route path="/signup" component={SignUpPage} />
        <PrivateRoute path="/quiz" component={QuizPage} />
        <PrivateRoute path="/finishQuiz" component={FinishQuizPage} />
        <PrivateRoute path="/" component={Dashboard} />
      </Switch>
    </BrowserRouter>
  </GlobalProvider>,
  document.getElementById('root')
);
