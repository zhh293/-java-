package com.hmall.geteway.router;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Component
@Slf4j
@RequiredArgsConstructor
public class DynamicRouterLoader {
    private final NacosConfigManager nacosConfigManager;
    private final RouteDefinitionWriter routeDefinitionWriter;
    private final Set<String> ids = new HashSet<>();
    private final String dataId = "dynamic-router.json";
    private final String group = "DEFAULT_GROUP";
    @PostConstruct
    public void load() throws NacosException {
        String configIfo = nacosConfigManager.getConfigService().getConfigAndSignListener(dataId, group, 5000, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                updateConfigInfo(configInfo);

            }

        });
        updateConfigInfo(configIfo);


    }
    public void updateConfigInfo(String confidInfo){
        List<RouteDefinition> list = JSONUtil.toList(confidInfo, RouteDefinition.class);
        for (String id : ids) {
            routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ids.clear();
        for (RouteDefinition routeDefinition : list) {
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            ids.add(routeDefinition.getId());
        }

    }

}
