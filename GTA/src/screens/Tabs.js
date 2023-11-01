import * as React from 'react';
import { Text, TouchableOpacity, View, StyleSheet } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

// 컴포넌트 import
import Login from './Login'; 
import Home from './Home'; 
import Map from './Map';
import Profile from './Profile';

// 아이콘 import
import Ionicons from 'react-native-vector-icons/Ionicons'
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5'
import { theme } from '../global/colors';

function LoginScreen() {
  return (
    <Login></Login>
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

const Tab = createBottomTabNavigator();

export default function App() {

  const nowLocation = "첨성대";
  
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
        headerTitle: () => (
          <View>
            <Text style={styles.titleText}>현재위치 : {nowLocation}</Text>
          </View>
        ),
        headerRight: () => (
          <TouchableOpacity
          style={styles.stampBtn}
          onPress={() => alert('스탬프 찍기!')}
          >
            <FontAwesome5 name='stamp' size={16} color='white' />
            <Text style={styles.stampText}>스탬프 찍기</Text>
          </TouchableOpacity>
        ),
        tabBarIcon: ({ focused, color, size }) => {
          let iconName;

          if (route.name === 'Home') {
            iconName = focused
              ? 'home'
              : 'home-outline';
          } else if (route.name === 'Map') {
            iconName = focused ? 'map' : 'map-outline';
          } else if (route.name === 'Profile') {
              iconName = focused ? 'person' : 'person-outline';
          }

          return <Ionicons name={iconName} size={size} color={color} />;
        },
        tabBarActiveTintColor: theme.color1,
        tabBarInactiveTintColor: 'gray',
      })}
      >
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Map" component={MapScreen} />
        <Tab.Screen name="Profile" component={ProfileScreen} />
      </Tab.Navigator>
    </NavigationContainer>
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
  }

});