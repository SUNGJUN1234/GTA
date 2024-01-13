
import AsyncStorage from '@react-native-async-storage/async-storage';

export const setStorageItem = async (key, value) => {
    try {
      await AsyncStorage.setItem(key, value);
      console.log(`${key} 스토리지 세팅 성공`);
    } catch (error) {
      console.error(`${key} 스토리지 세팅 실패:`, error);
      throw error; // 에러를 전파하여 필요한 곳에서 처리할 수 있도록 함
    }
  };
  
  
  export const getStorageItem = async (key) => {
    try {
      const value = await AsyncStorage.getItem(key);
      console.log(`${key} 스토리지 가져오기 성공`);
      return value;
    } catch (error) {
      console.error(`${key} 스토리지 가져오기 실패:`, error);
      throw error; // 에러를 전파하여 필요한 곳에서 처리할 수 있도록 함
    }
  };
  