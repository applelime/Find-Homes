package com.pearldonut.findhomes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class HouseNewsCrawling implements ApplicationRunner {
	public void run(ApplicationArguments args) throws Exception {
		// 데이터 업데이트가 필요할 때만 주석 풀고 사용
		// crawlNews();
	}

	public static void crawlNews() throws Exception {
		Connection conn = null;
		final String url = "jdbc:mysql://localhost:3306/findhomes?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
		final String user = "scott";  
		final String password = "tiger";
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			PreparedStatement pstat = conn.prepareStatement(
					"INSERT INTO news (title, content, link, news_date,company) VALUES (?,?,?,?,?)");    
            
			String day = Integer.toString(LocalDate.now().getDayOfMonth());
			String news_url = "https://land.naver.com/news/headline.naver?bss_ymd=202206" + day;
			
			Document doc = Jsoup.connect(news_url).get();
			Elements elements = doc.getElementsByAttributeValue("class", "headline_list");
			String link_pre ="https://land.naver.com/";
			
			Elements li_list = elements.select("li");
			
			for (int i = 0; i < li_list.size(); i++) {
				Elements dt_list = li_list.get(i).select("dt");
	
				Elements dd_tag = li_list.get(i).select("dd");
				Elements a_tag;
				if(dt_list.size()>=2)
					a_tag = dt_list.get(1).select("a");
				else
					a_tag = dt_list.get(0).select("a");
				Elements dd_span = li_list.get(i).select("span");
				
				System.out.println(a_tag.text());// 기사 제목
				System.out.println(a_tag.attr("href"));// 링크에는 앞에ㅔ https://land.naver.com/ 이거 붙여서 넣어야한다ㅏㅏㅏㅏ
				
				System.out.println(dd_tag.text());// 기사 내용
				System.out.println(dd_span.get(0).text());// 기사 출처
				System.out.println(dd_span.get(1).text());// 기사 날짜
				
				StringBuilder link = new StringBuilder();
				link.append(link_pre).append(a_tag.attr("href"));
				
				pstat.setString(1,a_tag.text());
				pstat.setString(2,dd_tag.text());
				pstat.setString(3,link.toString());
				pstat.setString(4,dd_span.get(1).text());
				pstat.setString(5,dd_span.get(0).text());
				
				pstat.executeUpdate();	
			}
			
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}
