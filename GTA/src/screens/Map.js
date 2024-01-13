import React, { useEffect, useState } from 'react';
import { View, Text } from 'react-native';
import { useAppContext } from '../global/AppContext';
import MapView, { Marker } from 'react-native-maps';
import { theme } from '../global/colors';

const Map = () => {
  const { position, setPosition , userInfo , setUserInfo , now , setNow } = useAppContext(); // 전역 변수

  if(!now){
    return;
  }

  return (
    <View style={{ flex: 1 }}>
      {position && position.lat !== 0? (
     <MapView
     style={{ flex: 1 }}
     initialRegion={{
       latitude: position.lat,
       longitude: position.lng,
       latitudeDelta: 0.01, // 작은 값으로 변경하여 더 확대
       longitudeDelta: 0.01, // 작은 값으로 변경하여 더 확대
     }}
   >
    {now.map((item, idx) => (
      <Marker 
        key={idx}
        coordinate={{
          latitude: parseFloat(item['touristAttractionsResponseRedisDto'].lat),
          longitude: parseFloat(item['touristAttractionsResponseRedisDto'].lng),
        }}
        title={item['touristAttractionsResponseRedisDto'].tourDestNm}
        description={item['touristAttractionsResponseRedisDto'].addrRoad?item['touristAttractionsResponseRedisDto'].addrRoad:item['touristAttractionsResponseRedisDto'].addrJibun}
        pinColor={theme.color1}
     />
    ))}
      <Marker
       coordinate={{
         latitude: position.lat,
         longitude: position.lng,
       }}
       title="현재 위치"
     />
     
   </MapView>
      ) : (
        <Text>위치 정보를 불러오는 중...</Text>
      )}
    </View>
  );
};

export default Map;
