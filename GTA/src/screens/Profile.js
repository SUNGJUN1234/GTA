import React, { useEffect, useState } from 'react';
import { View, StyleSheet, Text, TouchableOpacity, Dimensions, ScrollView, ImageBackground } from 'react-native';
import stampImage0 from '../../assets/stamp_0.png';
import stampImage1 from '../../assets/stamp_1.png';
import stampImage2 from '../../assets/stamp_2.png';
import stampImage3 from '../../assets/stamp_3.png';
import stampImage4 from '../../assets/stamp_4.png';
import stampImage5 from '../../assets/stamp_5.png';
import stampImage6 from '../../assets/stamp_6.png';
import stampImage7 from '../../assets/stamp_7.png';
import stampImage8 from '../../assets/stamp_8.png';
import stampImage9 from '../../assets/stamp_9.png';
import { theme } from '../global/colors';

import {
  LineChart,
  BarChart,
  PieChart,
  ProgressChart,
  ContributionGraph,
  StackedBarChart
} from "react-native-chart-kit";
import { useAppContext } from '../global/AppContext';

// 아이콘 import
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5'
import { createAxiosInstance } from '../global/AxiosInstance';
import { awsServer } from '../server';

const SCREEN_WIDTH = Dimensions.get('window').width;

const Profile = () => {
  const { position, setPosition , userInfo , setUserInfo , now , setNow } = useAppContext(); // 전역 변수

  const totalStamp = 83;
  const [ stampData, setStampData ] = useState([]);
  const [ stampCount, setStampCount] = useState(0);
  const [ loading, setLoading ] = useState(true);

  async function loadProfile (){
    const axiosInstance = createAxiosInstance(userInfo.accessToken, userInfo.refreshToken);

    const userResponse = await axiosInstance.get(awsServer.url + `/member/getUsersData`);
    const userId = userResponse.data.id;
    try {
      const stampResponse = await axiosInstance.get(awsServer.url + `/api/stamps/v3/findUsers/${userId}`);
      setStampData(stampResponse.data);
      setStampCount(stampResponse.data.length);
      // console.log(stampResponse.data);
    } catch (error) {
      console.log("스탬프 없다!");
      // 에러 처리 로직 추가 (예: 사용자에게 알림 표시)
    }
    setLoading(false);

  }

  useEffect(()=> {
      loadProfile();
  },[])



  const data = [
    {
      name: "미방문 관광지",
      population: totalStamp - stampCount,
      color: "orange",
      legendFontColor: "#000",
      legendFontSize: 15
    },
    {
      name: "방문 관광지",
      population: stampCount,
      color: "brown",
      legendFontColor: "#000",
      legendFontSize: 15
    },
  ];

  const chartConfig = {
    backgroundGradientFrom: "#1E2923",
    backgroundGradientFromOpacity: 0,
    backgroundGradientTo: "#08130D",
    backgroundGradientToOpacity: 0.5,
    color: (opacity = 1) => `rgba(26, 255, 146, ${opacity})`,
    strokeWidth: 2, // optional, default 3
    barPercentage: 0.5,
    useShadowColorFromDataset: false // optional
  };

  if(loading) {
    return;
  }

  return (
    <View style={styles.container}>
      <ScrollView
        contentContainerStyle={styles.scrollView}
        showsHorizontalScrollIndicator={false}
      >

        <View style={styles.rowView}>
          <Text style={styles.titleText}>진행률</Text>
        </View>

        <View>
          <PieChart
            data={data}
            width={SCREEN_WIDTH}
            height={160}
            chartConfig={chartConfig}
            accessor={"population"}
            backgroundColor={"transparent"}
            paddingLeft={"15"}
            center={[10, 10]}
            absolute
          />
        </View>

        <View style={styles.rowView}>
          <Text style={styles.titleText}>나의 스탬프</Text>
          <Text style={styles.moreText}>{stampCount} / {totalStamp}</Text>
        </View>
        
        {stampCount===0?
        (
          <View></View>
        )
        :
        (
        <View style={styles.stampCollectionView}>
          {stampData.map((item, idx) => {
            // 동적으로 이미지를 선택할 때는 switch 문이나 if-else 문을 사용
            let selectedImage;
            switch (item.touristAttractionsId % 10) {
              case 0:
                selectedImage = stampImage0;
                break;
              case 1:
                selectedImage = stampImage1;
                break;
              case 2:
                selectedImage = stampImage2;
                break;
              case 2:
                selectedImage = stampImage2;
                break;
              case 3:
                selectedImage = stampImage3;
                break;
              case 4:
                selectedImage = stampImage4;
                break;
              case 5:
                selectedImage = stampImage5;
                break;
              case 6:
                selectedImage = stampImage6;
                break;
              case 7:
                selectedImage = stampImage7;
                break;
              case 8:
                selectedImage = stampImage8;
                break;
              case 9:
                selectedImage = stampImage9;
                break;
              default:
                selectedImage = stampImage0;
            }

            return (
              <ImageBackground
                source={selectedImage}
                style={styles.stampImage}
                key={idx}
              >
                <View style={styles.stampView}>
                  <Text>No. {item.touristAttractionsId}</Text>
                  <Text style={styles.stampName}>{item.name}</Text>
                </View>
              </ImageBackground>
            );
          })}
        </View>
        )
        }
        
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  rowView: {
    padding: 12,
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  titleText:{
    color: 'black',
    fontSize: 16,
    fontWeight: '600',
  },
  moreText: {
    color: 'black',
  },
  stampCollectionView: {
    padding: 10,
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  stampImage: {
    width:(SCREEN_WIDTH - 80)/3,
    height:(SCREEN_WIDTH - 80)/3,
    margin: 10,
  },
  stampView: {
    width:(SCREEN_WIDTH - 80)/3,
    height:(SCREEN_WIDTH - 80)/3,
    borderRadius:500,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 4,
  },
  stampName: {
    paddingHorizontal: 14,
    marginTop: 4,
    marginBottom: 16,
    fontSize: 10,
    fontWeight: '900',
  }

});

export default Profile;