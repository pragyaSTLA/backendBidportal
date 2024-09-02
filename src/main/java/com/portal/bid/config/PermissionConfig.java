package com.portal.bid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PermissionConfig {

    @Bean
    public Map<String, String> permissionEndpointMap() {
        return Map.of(
                "VIEW_OPPORTUNITY", "GET:/api/opportunities",
                "ADD_OPPORTUNITY", "POST:/api/opportunities",
                "UPDATE_OPPORTUNITY", "PUT:/api/opportunities/{id}",
                "DELETE_OPPORTUNITY", "DELETE:/api/opportunities/{id}",
                "DEAL_STATUS_UPADATE", "PUT:/api/deal-status/{id}",
                "GO_NO_GO_STATUS_UPADATE", "PUT:/api/gonogostatus/{id}",
                "ADD_PLAN_ACTIONS", "POST:/api/plans",
                "CHARTS_VIEW", "GET:/api/opportunities",
                "SUMMARY_DASHBOARD_VIEW", "GET:/api/opportunities",
                "ADD_DEAL_STATUS", "POST:/api/deal-status"
        );
    }
}
