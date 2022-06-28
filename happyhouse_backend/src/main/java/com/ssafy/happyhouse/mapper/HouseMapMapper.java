package com.ssafy.happyhouse.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;


@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	int getTotalCount(ListParameterDto listParameterDto) throws Exception;
	List<HouseInfoDto> getAptRank(ListParameterDto listParamterDto) throws Exception;
	List<HouseInfoDto> getSearchList(ListParameterDto listParameterDto);
}
