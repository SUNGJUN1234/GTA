import React, { useEffect, useState } from 'react';
import { View, StyleSheet, Text, TouchableOpacity, Linking, } from 'react-native';
import { WebView } from 'react-native-webview';
import axios from 'axios';
import { awsServer } from '../server';
import { useAppContext } from '../global/AppContext';

const LoginHub = ({ navigation }) => {

  const { position, setPosition , userInfo , setUserInfo } = useAppContext(); // 전역 변수
  const url = awsServer.url+'/api/v1/touristAttractions';

  const requestUserInfo = async () => {
    console.log("동작");
    const userInfoResponse = await axios.get(awsServer.url + "/logout");
    const response = userInfoResponse.request._response;
    console.log(response);

    if (typeof response === 'string') {
        try {
          console.log("가자!");
          setUserInfo(JSON.parse(response));
          console.log(response);
          navigation.navigate('HomeScreen')
        } catch (error) {
          setUserInfo(null);
          console.log("userInfo != null");
        }
      } else {
        // 변수가 문자열이 아님
        console.log("userInfo != string");
      }

  }

  const getCode = async (target) => {
    console.log("dd");
    try {
      console.log(target);
    } catch (e) {
      console.log(e);
    }
  }


  useEffect(()=> {
    requestUserInfo();
  },[])

  return (
    <View style={styles.container}>
      <WebView
        style={{ flex: 1 }}
        source={{ uri: url }}
        javaScriptEnabled
        onMessage={(event) => {
          getCode(event.nativeEvent.data);
        }}
        originWhitelist={['*']} // Allow the WebView to post messages
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
});

export default LoginHub;
