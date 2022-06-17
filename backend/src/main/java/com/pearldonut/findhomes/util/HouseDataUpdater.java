package com.pearldonut.findhomes.util;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pearldonut.findhomes.model.HouseDealDto;
import com.pearldonut.findhomes.model.HouseInfoDto;

@Order(1)
@Component
public class HouseDataUpdater implements ApplicationRunner {
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 데이터 업데이트가 필요할 때만 주석 풀고 사용
//		updateAllData();
	} 
	
	public void updateAllData() {
		URL fileUrl = ClassLoader.getSystemResource("res/saledata");
		File folder = new File(fileUrl.getFile());
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
//				System.out.println(file.getName());
				System.out.println("-------------------------");
				System.out.println(file.getName());
				System.out.println("-------------------------");
				List<HouseInfoDto> infoList = getHouseInfoDataList(file, true);
				insertHouseInfo(infoList);
				
				List<HouseDealDto> dealList = getHouseDealDataList(file, true);
				insertHouseDeal(dealList);
			}
		}
		
		fileUrl = ClassLoader.getSystemResource("res/etcdata");
		folder = new File(fileUrl.getFile());
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
//				System.out.println(file.getName());
				System.out.println("-------------------------");
				System.out.println(file.getName());
				System.out.println("-------------------------");
				List<HouseInfoDto> infoList = getHouseInfoDataList(file, false);
				insertHouseInfo(infoList);
				
				List<HouseDealDto> dealList = getHouseDealDataList(file, false);
				insertHouseDeal(dealList);
			}
		}
		
		System.out.println("-------------------------");
		System.out.println("--- 매매 데이터 추가 완료 ---");
		System.out.println("-------------------------");
	}
	
	////////////////////////////////////
	// HouseInfo
	////////////////////////////////////
	
	// HouseInfo 테이블에 추가할 데이터 리스트를 생성
	public List<HouseInfoDto> getHouseInfoDataList(File file, boolean isSale) {

		String houseType = "";
		if (file.getName().contains("apt"))
			houseType = "apt";
		else 
			houseType = "officetel";
		
		final String kakaoMapRestApiKey = "42cdf4c5a0222da1597de0c18d650cd2"; 

		List<HouseInfoDto> HouseInfoList = new ArrayList<>(); 
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = "";
			while((line = reader.readLine()) != null) {
				HouseInfoDto houseInfo = new HouseInfoDto();
				String[] item = line.split("\t");
				
				houseInfo.setSigungu(item[0]);
				houseInfo.setHouseName(item[4]);
				houseInfo.setHouseType(houseType);
				
				// 매매데이터와 전/월세 데이터의 컬럼이 달라서 다르게 처리
				if (isSale) {
					if (item.length != 15) continue;
					if (item[10] == null || item[10].equals("null"))
						item[10] = "1900";
					houseInfo.setBuildYear(Integer.parseInt(item[10]));
					houseInfo.setRoadName(item[11]);
				} else {
					if (item.length != 17) continue;
					if (item[12] == null || item[12].equals("null"))
						item[12] = "1900";
					houseInfo.setBuildYear(Integer.parseInt(item[12]));
					houseInfo.setRoadName(item[13]);
				}
//				System.out.println(houseInfo.getRoadName());
				
				// 카카오 API로 dongCode(b_code), 경도(x = lng), 위도(y = lat) 받아오기
				String url = "https://dapi.kakao.com/v2/local/search/address.json?query="
						 + URLEncoder.encode(houseInfo.getSigungu() + " " + houseInfo.getRoadName(), "UTF-8");
				HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
				
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-Type", "application/json");
//				conn.setRequestProperty("Authorization", "KakaoAK " + kakaoMapRestApiKey);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setDefaultUseCaches(false);
				
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				String inputLine;
	            StringBuffer response = new StringBuffer();
	 
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            String responseData = response.toString();
	            System.out.println(responseData);
	            
	            JSONObject root = new JSONObject(responseData);
	            if (0 == root.length())
	            	continue;
	            
	            JSONArray documents = root.getJSONArray("documents");
	            if (0 == documents.length()) // 혹시나 json 데이터가 비어있다면 스킵
	            	continue;
	            JSONObject addressData = documents.getJSONObject(0);
	            JSONObject address = addressData.getJSONObject("address");
	            String bCode = address.getString("b_code");
	            String lng = addressData.getString("x");
	            String lat = addressData.getString("y");
	            // 
	            
	            houseInfo.setDongCode(bCode);
	            houseInfo.setLng(lng);
	            houseInfo.setLat(lat);
//	            System.out.println(houseInfo);
				
				HouseInfoList.add(houseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return HouseInfoList;
	}
	
	// 리스트의 값으로 HouseInfo 테이블에 삽입
	public void insertHouseInfo(List<HouseInfoDto> list) {
		Connection conn = null;
		final String url = "jdbc:mysql://localhost:3306/findhomes?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
		final String user = "scott";  
		final String password = "tiger";
		
		try {
	        conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            PreparedStatement pstat = conn.prepareStatement("INSERT IGNORE INTO house_info (sigungu, road_name, dong_code, house_name, house_type, build_year, lng, lat) VALUES (?,?,?,?,?,?,?,?)");    
            
            int num = 0;            
            for (HouseInfoDto houseInfo : list) {
                num++;
                pstat.setString(1, houseInfo.getSigungu());
                pstat.setString(2, houseInfo.getRoadName());
                pstat.setString(3, houseInfo.getDongCode());
                pstat.setString(4, houseInfo.getHouseName());
                pstat.setString(5, houseInfo.getHouseType());
                pstat.setInt(6, houseInfo.getBuildYear());
                pstat.setString(7, houseInfo.getLng());
                pstat.setString(8, houseInfo.getLat());
                pstat.addBatch();
                if(num>50000){
//                    System.out.println(pstat);
                    pstat.executeBatch();
                    conn.commit();
                    num=0;
                }
//                System.out.println(pstat);
                pstat.executeBatch();
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	////////////////////////////////////
	// HouseDeal
	////////////////////////////////////
	
	// HouseDeal 테이블에 추가할 데이터 리스트를 생성
		public List<HouseDealDto> getHouseDealDataList(File file, boolean isSale) {			
			List<HouseDealDto> HouseDealList = new ArrayList<>(); 
			try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line = "";
				while((line = reader.readLine()) != null) {
					HouseDealDto houseDeal = new HouseDealDto();
					String[] item = line.split("\t");
					
					houseDeal.setSigungu(item[0]);
					houseDeal.setHouseName(item[4]);
					
					if (isSale) {
						if (item.length != 15) continue;
						int dealYear = Integer.parseInt(item[6].substring(0,4));
						int dealMonth = Integer.parseInt(item[6].substring(4,6));
						int dealDay = Integer.parseInt(item[7]);
						houseDeal.setDealYear(dealYear);
						houseDeal.setDealMonth(dealMonth);
						houseDeal.setDealDay(dealDay);
						
						houseDeal.setArea(item[5]);
						houseDeal.setDealType("sale");
						houseDeal.setFloor(Integer.parseInt(item[9]));
						
						String sale = item[8].replace(",", "").replace("\"", "").trim();
						houseDeal.setSaleAmount(Integer.parseInt(sale));
					} else {
						if (item.length != 17) continue;
						int dealYear = Integer.parseInt(item[7].substring(0,4));
						int dealMonth = Integer.parseInt(item[7].substring(4,6));
						int dealDay = Integer.parseInt(item[8]);
						houseDeal.setDealYear(dealYear);
						houseDeal.setDealMonth(dealMonth);
						houseDeal.setDealDay(dealDay);
						
						houseDeal.setArea(item[6]);
						String dealType = item[5].equals("전세")? "jeonse" : "monthly";
						houseDeal.setDealType(dealType);
						houseDeal.setFloor(Integer.parseInt(item[11]));
						
						String sale = item[9].replace(",", "").replace("\"", "").trim();
						if (dealType.equals("jeonse")) {						
							houseDeal.setJeonseAmount(Integer.parseInt(sale));
						} else {
							houseDeal.setMonthlyDeposit(Integer.parseInt(sale));
							houseDeal.setMonthlyLease(Integer.parseInt(item[10]));
						}
					}
//		            System.out.println(houseDeal);
					
		            HouseDealList.add(houseDeal);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return HouseDealList;
		}
		
		// 리스트의 값으로 HouseDeal 테이블에 삽입
		public void insertHouseDeal(List<HouseDealDto> list) {
			Connection conn = null;
			final String url = "jdbc:mysql://localhost:3306/findhomes?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
			final String user = "scott";  
			final String password = "tiger";
			
			try {
		        conn = DriverManager.getConnection(url, user, password);
	            conn.setAutoCommit(false);
	            PreparedStatement pstat = conn.prepareStatement("INSERT IGNORE INTO house_deal (sigungu, house_name, deal_year, deal_month, deal_day, area, deal_type, floor, sale_amount, jeonse_amount, monthly_deposit, monthly_lease) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
	            
	            int num = 0;            
	            for (HouseDealDto houseDeal : list) {
	                num++;
	                pstat.setString(1, houseDeal.getSigungu());
	                pstat.setString(2, houseDeal.getHouseName());
	                pstat.setInt(3, houseDeal.getDealYear());
	                pstat.setInt(4, houseDeal.getDealMonth());
	                pstat.setInt(5, houseDeal.getDealDay());
	                pstat.setString(6, houseDeal.getArea());
	                pstat.setString(7, houseDeal.getDealType());
	                pstat.setInt(8, houseDeal.getFloor());
	                
	                pstat.setLong(9, houseDeal.getSaleAmount());
	                pstat.setLong(10, houseDeal.getJeonseAmount());
	                pstat.setLong(11, houseDeal.getMonthlyDeposit());
	                pstat.setLong(12, houseDeal.getMonthlyLease());
	                
	                pstat.addBatch();
	                if(num>50000){
//	                    System.out.println(pstat);
	                    pstat.executeBatch();
	                    conn.commit();
	                    num=0;
	                }
//	                System.out.println(pstat);
	                pstat.executeBatch();
	                conn.commit();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
		}	
}
