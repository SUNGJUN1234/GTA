import React, { useEffect, useState } from 'react';
import { View, StyleSheet, Text, TouchableOpacity, Linking, } from 'react-native';
import { WebView } from 'react-native-webview';
import axios from 'axios';
import { awsServer } from '../server';

const LoginHub = ({ navigation }) => {

  // const [loading , setLoading] = useState(true);

  const url = awsServer.url+'/api/v1/touristAttractions';

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
