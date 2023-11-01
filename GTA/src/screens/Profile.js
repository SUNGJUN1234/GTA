import React, { useEffect } from 'react';
import { View, StyleSheet, Text } from 'react-native';

const Profile = () => {

  return (
    <View style={styles.container}>
        <Text>Profile</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
});

export default Profile;