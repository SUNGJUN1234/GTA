import React, { useEffect, useRef } from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { Platform, View } from 'react-native';
import TabsNavigatorScreen from './src/screens/Tabs';
import { request, PERMISSIONS } from 'react-native-permissions';
import { useAppContext } from './src/global/AppContext';
import Geolocation from '@react-native-community/geolocation';
import SplashScreen from 'react-native-splash-screen';
import axios from 'axios'
import { NavigationContainer } from '@react-navigation/native';
import { awsServer } from './src/server';
import { setStorageItem, getStorageItem } from './src/global/AsyncStorage';


export default function App() {
  const navigationRef = useRef(null);
  const { position, setPosition , userInfo , setUserInfo , now , setNow } = useAppContext(); // 전역 변수

  const requestUserInfo = async() => {
    const accessToken = await getStorageItem('accessToken');
    const refreshToken = await getStorageItem('refreshToken');

    setUserInfo({
      accessToken : accessToken,
      refreshToken : refreshToken
    })

    const currentPage = navigationRef.current?.getCurrentRoute()?.name;

    if(accessToken && refreshToken && currentPage === "LoginHub"){
      navigationRef.current?.navigate('HomeScreen');
  }
  }

  const requestLocationPermission = async () => {
    try {
      const permissionResponse = await request(PERMISSIONS.ANDROID.ACCESS_FINE_LOCATION);
      if (permissionResponse === 'granted') {
        return true;
      } else {
        console.log('권한이 거부되었습니다.');
        return false;
      }
    } catch (error) {
      console.error('권한 요청 중 오류 발생:', error);
      return false;
    }
  };

  let watchId = null; // 위치 감시 ID를 저장할 변수

  const fetchLocation = async () => {
    const hasLocationPermission = await requestLocationPermission();
  
    if (hasLocationPermission) {
      // 이미 위치 감시가 활성화되어 있다면 중복 실행을 방지
      if (watchId !== null) {
        console.log('위치 감시가 이미 활성화되어 있습니다.');
        return;
      }
  
      // Geolocation.watchPosition 호출하여 watchId 저장
      watchId = Geolocation.watchPosition(
        position => {
          const { latitude, longitude } = position.coords;
          setPosition({ lat: latitude, lng: longitude });
        },
        error => {
          console.error('위치 업데이트 중 오류 발생:', error);
        },
        {
          enableHighAccuracy: true, // 높은 정확도 요구
          timeout: 20000, // 20초 타임아웃
          maximumAge: 60000 // 60초 이내의 위치 정보 재사용
        }
      );
    }
  };
  
  const stopWatchingLocation = () => {
    if (watchId !== null) {
      Geolocation.clearWatch(watchId); // 위치 감시 종료
      watchId = null;
      console.log('위치 감시가 중지되었습니다.');
    }
  };
  
  useEffect(() => {
    const initializeApp = async () => {
      try {
        setTimeout(() => {
          SplashScreen.hide();
          requestUserInfo();
          fetchLocation(); // 스플래시가 활성화 된 후 위치 정보 요청
        }, 2000); // 스플래시 활성화 시간 2초
      } catch (e) {
        console.error('오류 발생:', e.message);
      }
    };
  
    initializeApp();
  }, []); // 빈 배열을 전달하여 한 번만 실행되도록 함



  return (
  <NavigationContainer ref={navigationRef}>
    <SafeAreaProvider>
      <View style={{ flex: 1 }}>
          <TabsNavigatorScreen />
      </View>
    </SafeAreaProvider>
  </NavigationContainer>
  );
}
