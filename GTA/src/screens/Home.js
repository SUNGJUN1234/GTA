import React, { useEffect, useRef, useState } from 'react';
import { View, StyleSheet, Text, Image, Dimensions, ScrollView, } from 'react-native';
import { theme } from '../global/colors';
import Swiper from 'react-native-swiper';

import NearCard from '../components/NearCard';

const SCREEN_WIDTH = Dimensions.get('window').width;

const Home = () => {
  // 현재 관광지 데이너
  const infoText = "첨성대는 경상북도 경주시 반월성 동북쪽에 위치한 신라 중기의 석조 건축물로, 선덕여왕 때에 세워진 세계에서 현존하는 가장 오래된 천문대 중 하나이다. 1962년 12월 20일 국보 제31호로 지정되었다."
  const [bannerData, setBannerData] = useState([
    { url: require('../../assets/test1.png') },
    { url: require('../../assets/test2.png') },
    { url: require('../../assets/test3.png') },
  ]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const swiperRef = useRef(null);

  // 주변 관광지 데이터
  const nearData = [
    {
      imgUrl : require('../../assets/test1.png'),
      name : '광주광역시공원&광주광역시향교',
      distance : 116,
      facilities : '화장실 + 주차장',
      info : '선비들의 정신이 살아 있음을 느낄 수 있음',
      address: '광주광역시 광산구 산월로21번길 26',
    },
    {
      imgUrl : require('../../assets/test2.png'),
      name : '포충사',
      distance : 355,
      facilities : '화장실 + 주차장',
      info : '광주광역시 남구 원산동에 있는 고경명 등 임진왜란 때의 의병 5명의 충절을 기리는 사당',
      address: '광주광역시 광산구 산월로21번길 26',
    },
    {
      imgUrl : require('../../assets/test3.png'),
      name : '무양서원',
      distance : 1355,
      facilities : '화장실 + 주차장',
      info : '무양서원은 고려 인종 때 어의이면서 명신인 장경공 최사전을 중심으로 그의 후손 4명(손암 최윤덕, 금남 최부, 문절공 유희춘, 충열공 나덕헌)을 모시고 있는 서원',
      address: '광주광역시 광산구 산월로21번길 26',
    },
  ];

  useEffect(() => {
    if (bannerData.length > 0) {
      const timer = setInterval(() => {
        swiperRef.current.scrollBy(1);
      }, 3000);

      return () => clearInterval(timer);
    }
  }, [currentIndex, bannerData]);

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

      <Text style={styles.nearText}>가까운 광광지 TOP 3</Text>
          {nearData.map((item, idx) => (
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
