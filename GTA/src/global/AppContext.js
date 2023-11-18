import React, { createContext, useContext, useState } from 'react';

// Context 생성
const AppContext = createContext();

// 커스텀 훅 생성
export const useAppContext = () => {
  return useContext(AppContext);
};

// Context Provider
export const AppContextProvider = ({ children }) => {
  const [ position , setPosition] = useState({
    lat : 0,
    lng : 0,
  });

  const [ userInfo , setUserInfo] = useState(null);

  const [ now , setNow] = useState({});

  return (
    <AppContext.Provider value={{ position, setPosition , userInfo , setUserInfo , now , setNow}}>
      {children}
    </AppContext.Provider>
  );
};