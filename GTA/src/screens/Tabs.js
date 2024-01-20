import React, { useEffect, useRef, useState } from 'react';
import { Text, TouchableOpacity, View, StyleSheet } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { useAppContext } from '../global/AppContext';
import axios from 'axios'
import { createAxiosInstance } from '../global/AxiosInstance';
import { setStorageItem, getStorageItem } from "../global/AsyncStorage";
import { useNavigation } from '@react-navigation/native';

// 컴포넌트 import
import LoginHub from './LoginHub'; 
import Home from './Home'; 
import Map from './Map';
import Profile from './Profile';

// 아이콘 import
import Ionicons from 'react-native-vector-icons/Ionicons'
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5'
import { theme } from '../global/colors';
import { awsServer } from '../server';

const Tab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();

function LoginScreen({ navigation, route }) {
  useEffect(()=>{
    navigation.setOptions({tabBarStyle : {display:'none'}})
  },[])
  return (
    <Stack.Navigator initialRouteName='LoginHub'>
      <Stack.Screen name="LoginHub" component={LoginHub} options={{headerShown:false}}/>
    </Stack.Navigator>
  )
}

function HomeScreen() {
  return (
    <Home></Home>
  );
}

function MapScreen() {
  return (
    <Map></Map>
  );
}

function ProfileScreen() {
  return (
    <Profile></Profile>
  );
}

export default function App() {
  const { position, setPosition , userInfo , setUserInfo , now , setNow } = useAppContext(); // 전역 변수
  const [loading , setLoading] = useState(true);
  const nowLocation = "첨성대";
  const navigation = useNavigation(); // useNavigation 훅을 이용해 navigation 객체 가져오기

  // 유저 정보 불러오기
  useEffect(()=>{
    setLoading(false);
  },[]);

  const stamping = async () => {
    try {
      const axiosInstance = createAxiosInstance(userInfo.accessToken, userInfo.refreshToken);

      const userResponse = await axiosInstance.get(awsServer.url + `/member/getUsersData`);
      const userId = userResponse.data.id;
      const data = {
        touristAttractionsId: now[0]['touristAttractionsResponseRedisDto'].id,
        usersId: userId,
        name: now[0]['touristAttractionsResponseRedisDto'].tourDestNm,
        issueDate: null,
        expirationDate: null,
      };

      await axiosInstance.post(
        awsServer.url + `/api/stamps/v3/save`,
        data,
      ).then((res) => {
        alert(`${data.name} 스탬프 쾅!`)
      });
    } catch (e) {
      alert('이미 해당 스탬프가 있습니다.')
    }
  };

  const logout = async () => {
    try {
      alert('로그아웃 되었습니다.')
      await setStorageItem('accessToken', '');
      await setStorageItem('refreshToken', '');
      setUserInfo(null);
      navigation.navigate('LoginScreen');
    }catch (e) {
      console.log(e);
    }
  }

  if (loading) {
    return;
  }
  
  return (
    <Tab.Navigator
      initialRouteName={userInfo === null ? 'LoginScreen': 'HomeScreen'}
      screenOptions={({ route }) => ({
      headerTitle: () => (
        <View>
          <Text style={styles.titleText}>현재위치 : {!now[0] ?'...검색중...' : now[0]['touristAttractionsResponseRedisDto'].tourDestNm }</Text>
        </View>
      ),
      headerRight: () => {
        if (route.name === 'ProfileScreen') {
          return (
            <TouchableOpacity
              style={styles.stampBtn}
              onPress={() => logout()}
            >
              <Text style={styles.logoutText}>로그아웃</Text>
            </TouchableOpacity>
          );
        } else {
          return (
            <TouchableOpacity
              style={styles.stampBtn}

              onPress={() => stamping(now)}
            >
              <FontAwesome5 name='stamp' size={16} color='white' />
              <Text style={styles.stampText}>스탬프 찍기</Text>
            </TouchableOpacity>
          );
        }
      },
      tabBarIcon: ({ focused, color, size }) => {
        let iconName;

        if (route.name === 'HomeScreen') {
          iconName = focused
            ? 'home'
            : 'home-outline';
        } else if (route.name === 'MapScreen') {
          iconName = focused ? 'map' : 'map-outline';
        } else if (route.name === 'ProfileScreen') {
            iconName = focused ? 'person' : 'person-outline';
        }

        return <Ionicons name={iconName} size={size} color={color} />;
      },
      tabBarActiveTintColor: theme.color1,
      tabBarInactiveTintColor: 'gray',
    })}
    >
      <Tab.Screen name="LoginScreen" component={LoginScreen}  options={{ tabBarButton: () => null ,headerShown:false }}></Tab.Screen>
      <Tab.Screen name="HomeScreen" component={HomeScreen} />
      <Tab.Screen name="MapScreen" component={MapScreen} />
      <Tab.Screen name="ProfileScreen" component={ProfileScreen} options={{unmountOnBlur: true,}} />
    </Tab.Navigator>

  );
}


const styles = StyleSheet.create({
  titleText:{
    color: 'black',
  },
  stampBtn:{
    flexDirection:"row",
    backgroundColor: theme.color1,
    paddingHorizontal: 8,
    paddingVertical: 4,
    marginRight: 16,
    borderRadius: 4,
  },
  stampText: {
    color: 'white',
    marginLeft: 8,
  },
  logoutText : {
    color: 'white',
  }

});