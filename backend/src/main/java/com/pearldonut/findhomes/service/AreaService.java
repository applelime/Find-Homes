package com.pearldonut.findhomes.service;

import java.util.List;
import java.util.Map;

public interface AreaService {
	List<Map<String, String>> sidoList();
	List<Map<String, String>> gugunList(String sidoCode);
	List<Map<String, String>> dongList(String gugunCode);
}
