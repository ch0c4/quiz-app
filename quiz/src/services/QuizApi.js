export async function signUp(email, password) {
  const myHeaders = new Headers();
  myHeaders.append('Content-Type', 'application/json');

  const raw = JSON.stringify({ email: email, password: password });

  const requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: raw,
    redirect: 'follow',
  };

  const response = await fetch('http://localhost:8080/v1/customers/', requestOptions);
  const json = await response.json();
  if (json.code && json.code !== 201) {
    throw new Error(json.message);
  }
  return json;
}

export async function login(email, password) {
  const myHeaders = new Headers();
  myHeaders.append('Content-Type', 'application/json');

  const raw = JSON.stringify({ email: email, password: password });

  const requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: raw,
    redirect: 'follow',
  };

  const response = await fetch('http://localhost:8080/v1/customers/login', requestOptions);
  if (response.status !== 200) {
    throw new Error(JSON.stringify(response));
  }
  const json = await response.json();
  return json;
}

export async function fetchQuizzesByCustomerId(customerId) {
  const requestOptions = {
    method: 'GET',
    redirect: 'follow',
  };

  const response = await fetch('http://localhost:8080/v1/quizzes/customers/1', requestOptions);
  if (response.status === 204) {
    return { quizzes: [] };
  }

  if (response.status !== 200) {
    throw new Error(JSON.stringify(response));
  }

  const json = await response.json();
  return json;
}

export async function startQuiz(customerId, category, difficulty) {
  const myHeaders = new Headers();
  myHeaders.append('Content-Type', 'application/json');

  const raw = JSON.stringify({ category: category, difficulty: difficulty });

  const requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: raw,
    redirect: 'follow',
  };

  const response = await fetch(`http://localhost:8080/v1/quizzes/customers/${customerId}`, requestOptions);
  if (response.status !== 200) {
    throw new Error(JSON.stringify(response));
  }

  const json = await response.json();
  return json;
}

export async function answeredQuestion(customerId, quizId, questionId, answer, answerTime) {
  const myHeaders = new Headers();
  myHeaders.append('Content-Type', 'application/json');

  const raw = JSON.stringify({ answer: answer, answerTime: answerTime });

  const requestOptions = {
    method: 'PUT',
    headers: myHeaders,
    body: raw,
    redirect: 'follow',
  };

  const response = await fetch(
    `http://localhost:8080/v1/answers/questions/${questionId}/quizzes/${quizId}/customers/${customerId}`,
    requestOptions
  );

  if (response.status !== 200) {
    throw new Error(JSON.stringify(response));
  }

  const json = await response.json();
  return json;
}

export async function fetchStat(quizId) {
  const requestOptions = {
    method: 'GET',
    redirect: 'follow',
  };

  const response = await fetch(`http://localhost:8080/v1/quizzes/${quizId}`, requestOptions);

  if (response.status !== 200) {
    throw new Error(JSON.stringify(response));
  }

  const json = await response.json();
  return json;
}
