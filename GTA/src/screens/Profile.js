import React, { useEffect } from 'react';
import { View, StyleSheet, Text, TouchableOpacity, Dimensions, ScrollView } from 'react-native';
import { theme } from '../global/colors';

import {
  LineChart,
  BarChart,
  PieChart,
  ProgressChart,
  ContributionGraph,
  StackedBarChart
} from "react-native-chart-kit";

// 아이콘 import
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5'

const SCREEN_WIDTH = Dimensions.get('window').width;

const Profile = () => {

  const totalStamp = 83;
  const myStamp = 6;

  const data = [
    {
      name: "전체 관광지",
      population: totalStamp,
      color: "orange",
      legendFontColor: "#000",
      legendFontSize: 15
    },
    {
      name: "방문 관광지",
      population: myStamp,
      color: "pink",
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
          <Text style={styles.moreText}>{myStamp} / {totalStamp}</Text>
        </View>
        
        <View style={styles.stampCollectionView}>
          <View style={styles.stampView}>
            <Text>스탬프</Text>
          </View>
          <View style={styles.stampView}>
            <Text>스탬프</Text>
          </View>
          <View style={styles.stampView}>
            <Text>스탬프</Text>
          </View>
          <View style={styles.stampView}>
            <Text>스탬프</Text>
          </View>
          <View style={styles.stampView}>
            <Text>스탬프</Text>
          </View>
          <View style={styles.stampView}>
            <Text>스탬프</Text>
          </View>
        </View>
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
  stampView: {
    width:(SCREEN_WIDTH - 80)/3,
    height:(SCREEN_WIDTH - 80)/3,
    margin: 10,
    backgroundColor:'orange',
    borderRadius:500,
    justifyContent: 'center',
    alignItems: 'center',
  },
  scrollView: {

  },

});

export default Profile;