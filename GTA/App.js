import React, { useEffect } from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { Platform, View } from 'react-native';
import TabsNavigatorScreen from './src/screens/Tabs';
import { request, PERMISSIONS } from 'react-native-permissions';
import { useAppContext } from './src/global/AppContext';
import Geolocation from '@react-native-community/geolocation';
import SplashScreen from 'react-native-splash-screen';

export default function App() {
  const { position, setPosition } = useAppContext(); // 전역 변수

  const fetchLocation = async () => {
    let permissionResponse;
    if (Platform.OS === 'android') {
      permissionResponse = await request(PERMISSIONS.ANDROID.ACCESS_FINE_LOCATION);

      if (permissionResponse === 'granted') {
        // 위치 업데이트 함수 정의
        const updateLocation = () => {
          Geolocation.getCurrentPosition(
            position => {
              const { latitude, longitude } = position.coords;
              setPosition({ lat: latitude, lng: longitude });
            },
            error => {
              console.log(error);
              // 사용자에게 에러 알림
            },
            { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 }
          );
        };

        // 초기 위치 불러오기
        updateLocation();

        // 일정한 간격으로 위치 업데이트
        const updateInterval = setInterval(updateLocation, 60000); // 60초마다 업데이트
        
        // 언마운트 시에 clearInterval로 업데이트를 중단합니다.
        return () => clearInterval(updateInterval);
      } else {
        // 권한 거부 처리
        console.log('권한이 거부되었습니다.');
      }
    } else {
      // iOS 권한 요청 코드
      console.log('iOS 권한 요청 코드 추가 필요');
    }
  };

  useEffect(() => {
    try {
      setTimeout(() => {
        SplashScreen.hide();
        fetchLocation(); // 스플래시가 활성화 된 후 위치 정보 요청
      }, 1000); // 스플래시 활성화 시간 2초
    } catch (e) {
      console.log(e.message);
    }
  }, []);

  return (
    <SafeAreaProvider>
      <View style={{ flex: 1 }}>
        <TabsNavigatorScreen />
      </View>
    </SafeAreaProvider>
  );
}
