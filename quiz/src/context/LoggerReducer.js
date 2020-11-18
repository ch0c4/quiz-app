import { useReducer, useEffect, useRef, useMemo } from 'react';
import { initialState } from './GlobalState';

function enchanceDispatchWithLogger(dispatch) {
  return function (action) {
    console.groupCollapsed('Action Type: ', action.type);
    return dispatch(action);
  };
}

export function useReducerWithLogger(reducer, initState) {
  let prevState = useRef(initialState);
  const [state, dispatch] = useReducer(reducer, initState);

  const dispatchWithLogger = useMemo(() => {
    return enchanceDispatchWithLogger(dispatch);
  }, [dispatch]);

  useEffect(() => {
    if (state !== initialState) {
      console.log('Prev state: ', prevState.current);
      console.log('Next state: ', state);
      console.groupEnd();
    }
    prevState.current = state;
  }, [state]);

  return [state, dispatchWithLogger];
}
