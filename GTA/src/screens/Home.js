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

  const { position, setPosition , userInfo , setUserInfo , now , setNow } = useAppContext(); // 전역 변수
  const [loading, setLoading] = useState(true);
  const [allTaArr, setAllTaArr] = useState([]);
  const [nearDataArr, setNearDataArr] = useState([]);

  // 현재 관광지 데이너
   const [bannerData, setBannerData] = useState([
    { url: require('../../assets/test1.png') },
    { url: require('../../assets/test2.png') },
    { url: require('../../assets/test3.png') },
  ]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const swiperRef = useRef(null);

  const loadHome = async () => {
    try {
      // const allReponse = await axios.get(awsServer.url + "/api/v1/touristAttractions");
      // setAllTaArr(allReponse.data);
      const nearResponse = await axios.get(awsServer.url + `/api/touristAttractions/v3/find/near/4/${position.lat}/${position.lng}`);
      console.log(nearResponse.data[0]);
      setNow(nearResponse.data);
      setNearDataArr(nearResponse.data);

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
        <Text style={styles.infoText}>{now[0]['touristAttractionsResponseRedisDto'].tourDestIntro}</Text>
        <Image source={require('../../assets/line1.png')} style={styles.lineImg} />
      </View>

      <Text style={styles.nearText}>가까운 관광지 TOP 3</Text>
          {nearDataArr.map((item, idx) => (
            idx !== 0 ? <NearCard key={idx} data={item} /> : null 
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
