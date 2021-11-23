package eventlistener.controller;

import eventlistener.model.statistic.RechartDataDTO;
import eventlistener.model.statistic.Statistic;
import eventlistener.service.StatistikService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistik")
public class StatistikController {

    StatistikService statistikService;

    public StatistikController(StatistikService statistikService) {
        this.statistikService = statistikService;
    }

    @GetMapping
    public List<Statistic> getAllStatistikObjects(){
        return statistikService.getAllStatistikObjects();
    }

    @GetMapping("/{action}")
    public List<Statistic> getAllStatistikObjectsByAction(@PathVariable String action){
        return statistikService.getAllStatisticObjectsByAction(action);
    }

    @GetMapping("/rechart")
    public List<RechartDataDTO> getRechartData(){
        return statistikService.getRechartData();
    }

}
