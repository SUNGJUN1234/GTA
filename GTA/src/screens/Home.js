import React, { useEffect, useRef, useState } from 'react';
import { View, StyleSheet, Text, Image, Dimensions, ScrollView, } from 'react-native';
import { theme } from '../global/colors';
import Swiper from 'react-native-swiper';
import axios from 'axios';
import { awsServer } from '../server';
import { useAppContext } from '../global/AppContext';
import NearCard from '../components/NearCard';

const SCREEN_WIDTH = Dimensions.get('window').width;

const Home = () => {

  const { position, setPosition , userInfo , setUserInfo } = useAppContext(); // 전역 변수
  const [loading, setLoading] = useState(true);
  const [allTaArr, setAllTaArr] = useState([]);
  const [nearDataArr, setNearDataArr] = useState([]);

  // 현재 관광지 데이너
  const infoText = "첨성대는 경상북도 경주시 반월성 동북쪽에 위치한 신라 중기의 석조 건축물로, 선덕여왕 때에 세워진 세계에서 현존하는 가장 오래된 천문대 중 하나이다. 1962년 12월 20일 국보 제31호로 지정되었다."
  const [bannerData, setBannerData] = useState([
    { url: require('../../assets/test1.png') },
    { url: require('../../assets/test2.png') },
    { url: require('../../assets/test3.png') },
  ]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const swiperRef = useRef(null);

  const loadHome = async () => {
    try {
      const allReponse = await axios.get(awsServer.url + "/api/v1/touristAttractions");

      setAllTaArr(allReponse.data);

      const nearResponse = await axios.get(awsServer.url + `/api/v2/touristAttractions/coordinate/near/4/${position.lat}/${position.lng}`);
      const updatedNearDataArr = nearResponse.data.map((item) => ({ 
        ...item, 
        distance: parseInt((Math.sqrt(Math.pow((Math.abs(item.lat) - Math.abs(position.lat)), 2) + Math.pow(Math.abs(item.lng) - Math.abs(position.lng), 2)))*111000)
      }));
      setNearDataArr(updatedNearDataArr);

    } catch (e) {
      console.log(e);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if(position.lat !== 0){
      loadHome();
    }
  }, [position])

  useEffect(() => {
    if (bannerData.length > 0) {
      const timer = setInterval(() => {
        swiperRef.current.scrollBy(1);
      }, 3000);

      return () => clearInterval(timer);
    }
  }, [currentIndex, bannerData]);


  if(loading) {
    return;
  }

  return (
    <View style={styles.container}>
      <ScrollView
        contentContainerStyle={styles.scrollView}
        showsHorizontalScrollIndicator={false}
      >

      <View style={styles.bannerTopView}>
        <Swiper
          ref={swiperRef}
          loop={true}
          showsPagination={true}
          activeDotStyle={{ backgroundColor: theme.color1 }}
          paginationStyle={{ bottom: 10 }}
        >
          {bannerData.map((item, index) => (
            <View style={styles.slide} key={index}>
              <Image source={item.url} style={styles.bannerImg} />
            </View>
          ))}
        </Swiper>
      </View>

      <View style={styles.infoView}>
        <Image source={require('../../assets/line1.png')} style={styles.lineImg} />
        <Text style={styles.infoText}>{infoText}</Text>
        <Image source={require('../../assets/line1.png')} style={styles.lineImg} />
      </View>

      <Text style={styles.nearText}>가까운 관광지 TOP 3</Text>
          {nearDataArr.map((item, idx) => (
            <NearCard key={idx} data={item} />
          ))}
      </ScrollView>

    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  bannerTopView:{
    height: SCREEN_WIDTH/2,
  },
  lineImg: {
    width: SCREEN_WIDTH /2,
    height: 20,
    marginVertical: 16,
  },
  infoView:{
    paddingHorizontal: 12,
    alignItems: 'center',
  },
  infoText:{

  },
  nearText:{
    color: 'black',
    paddingVertical: 12,
    paddingHorizontal: 12,
    fontSize: 16,
    fontWeight: '600',
  },
  slide: {
    flex: 1,
    position: 'relative',
    justifyContent: 'center',
    alignItems: 'center',
    width: SCREEN_WIDTH,
  },
  bannerImg: {
    width: '100%',
    height: '100%',
    resizeMode: 'cover',
  },
  scrollView: {

  },

});

export default Home;
