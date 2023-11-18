import React, { useEffect } from 'react';
import { View, StyleSheet, Text, Image, Dimensions } from 'react-native';
import { theme } from '../global/colors';

const SCREEN_WIDTH = Dimensions.get('window').width;

const NearCard = ({data}) => {
  return (
    <View style={styles.nearCardTopView}>
      <Image source={require('../../assets/line2.png')} style={styles.lineImg} />
      <View style={styles.upView}>
        <View style={styles.imgView}>
          <Image source={data.imgUrl ? data.imgUrl : require('../../assets/test2.png') } style={styles.cardImg}/>
        </View>
        <View style={styles.detailView}>
          <Text style={styles.nameText}>{data.tourDestNm}</Text>
          <Text>거리 : {data.distance}m</Text>
          <Text>시설 : {data.publicConvFcltInfo}</Text>
          <Text>주소 : {data.addrJibun? data.addrJibun:data.addrRoad}</Text>
        </View>
      </View>
      <View style={styles.downView}>
        <View style={styles.infoView}>
          <Text>{data.info}</Text>
        </View>
      </View>


    </View>
  );
};

const styles = StyleSheet.create({
  nearCardTopView: {
    paddingHorizontal: 12,
    alignItems: 'center',
  },
  upView: {
    flexDirection: 'row',
  },
  downView: {

  },
  imgView: {
    width: 100,
    height: 100,
  },
  cardImg: {
    width: '100%',
    height: '100%',
    resizeMode: 'cover',
  },
  detailView: {
    width: SCREEN_WIDTH - 100 - 24 - 12,
    marginLeft: 12,
    justifyContent:'space-between',
  },
  infoView: {
    width: SCREEN_WIDTH - 24,
    paddingVertical: 12,
  },  
  nameText: {
    color: 'black',
    fontSize: 16,
    fontWeight: '600',
  },
  lineImg: {
    width: SCREEN_WIDTH /2,
    height: 20,
    marginVertical: 16,
  },

});

export default NearCard;