import axios from 'axios';
import { awsServer } from '../server';

export const createAxiosInstance = (accessToken, refreshToken) => {
  return axios.create({
    baseURL: awsServer.url,
    headers: {
      'Authorization': `Bearer ${accessToken}`,  // 헤더에 AccessToken 추가
      'Cookie': `${refreshToken.split(';')[0]}`,   // Cookie에 RefreshToken 추가
    },
  });
};
