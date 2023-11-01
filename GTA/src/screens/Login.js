import React, { useEffect } from 'react';
import { View, StyleSheet, Text } from 'react-native';

const Login = () => {

  return (
    <View style={styles.container}>
        <Text>Login</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
});

export default Login;