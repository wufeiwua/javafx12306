package org.javafx12306.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.javafx12306.entity.Station;
import org.javafx12306.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

public class APIUtil {
    private static final String API_STATION_NAME = "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9191";
    private static final String API_LEFT_TICKET = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date={}&leftTicketDTO.from_station={}&leftTicketDTO.to_station={}&purpose_codes={}";

    public static List<Ticket> getTicketList(String fromCode, String toCode, String date) {
        String queryAPI = getQueryAPI(fromCode, toCode, date);
        String json = getTicket(queryAPI);
        JSONObject jsonObject = JSONUtil.parseObj(json);
        if (null == jsonObject) {
            return new ArrayList<>();
        }
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray result = data.getJSONArray("result");
        List<Ticket> tickets = new ArrayList<>();
        if (null != result && result.size() > 0) {
            for (Object o : result) {
                String item = (String) o;
                String[] splits = item.split("\\|");
                Ticket ticket = new Ticket();
                ticket.setCc(splits[3]);// 车次信息
                ticket.setSfmz(splits[6]); // 出发地代号
                ticket.setDdmz(splits[7]);// 目的地代号
                ticket.setCfsj(splits[8]);// 出发时间
                ticket.setDdsj(splits[9]); // 到达时间
                ticket.setLishi(splits[10]);// 历时
                ticket.setGjrw(0 == splits[21].length() ? "--" : splits[21]); // 高级软卧
                ticket.setRw(0 == splits[23].length() ? "--" : splits[23]); // 软卧
                ticket.setWz(0 == splits[26].length() ? "--" : splits[26]); // 无座
                ticket.setYw(0 == splits[28].length() ? "--" : splits[28]); // 硬卧
                ticket.setYz(0 == splits[29].length() ? "--" : splits[29]); // 硬座
                ticket.setEd(0 == splits[30].length() ? "--" : splits[30]); // 二等座
                ticket.setYd(0 == splits[31].length() ? "--" : splits[31]);// 一等座
                ticket.setTd(0 == splits[32].length() ? "--" : splits[32]); // 商务，特等座
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public static List<Station> getStationList() {
        String stations = getStations();
        String[] splits = stations.split("\\|");
        List<Station> stationList = new ArrayList<>();
        for (int i = 0; i + 4 < splits.length; i += 5) {
            Station station = new Station();
            station.setAt(splits[i + 0]);
            station.setName(splits[i + 1]);
            station.setCode(splits[i + 2]);
            station.setFullPinYin(splits[i + 3]);
            station.setShortPinYin(splits[i + 4]);
            stationList.add(station);
        }
        return stationList;
    }

    // private static String

    private static String getStations() {
        String js = HttpUtil.get(API_STATION_NAME);
        if (null != js && js.length() > 0) {
            String substring = js.substring(js.indexOf("'") + 1, js.indexOf("';"));
            return substring;
        }
        return js;
    }

    private static String getQueryAPI(String from_station, String to_station,String date) {
        DateTime parse = DateUtil.parse(date);
        String dateStr = DateUtil.format(parse, "yyyy-MM-dd");
        return StrUtil.format(API_LEFT_TICKET, dateStr, from_station, to_station, "ADULT");
    }

    private static String getQueryAPI(String train_date, String from_station, String to_station, String purpose_codes) {
        return StrUtil.format(API_LEFT_TICKET, train_date, from_station, to_station, purpose_codes);
    }

    private static String getTicket(String url) {
        String body = HttpRequest.get(url)
                .cookie("_uab_collina=162139648735320603981687; JSESSIONID=DDE572172DEC6A46F2355B091708684F; BIGipServerotn=1105723658.64545.0000; BIGipServerpassport=904397066.50215.0000; RAIL_EXPIRATION=1621658129730; RAIL_DEVICEID=JrNQj-LbAvzQOQ65nO9s5SSnPlD1vPjD7XeUbUWdyiDEbO7VpRbNF1QDm77NCdApQGJRLtWQeg4rvF7IRVyfHUYx8KmJbr7vhYlGXcciOURcfhkAxNWRoZwDun2OvxVIlrj62MTmrslybeV5ZRPs3NiaO4297J-y; route=9036359bb8a8a461c164a04f8f50b252; _jc_save_fromStation=%u5317%u4EAC%2CBJP; _jc_save_toStation=%u4E0A%u6D77%2CSHH; _jc_save_fromDate=2021-05-19; _jc_save_toDate=2021-05-19; _jc_save_wfdc_flag=dc; BIGipServerportal=2949906698.17695.0000")
                .header(Header.CONNECTION, "keep-alive")
                .header("Host", "kyfw.12306.cn")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
                .execute().body();
        return body.length() == 0 ? "{}" : body;
    }
}
