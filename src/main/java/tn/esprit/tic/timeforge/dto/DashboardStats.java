package tn.esprit.tic.timeforge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class DashboardStats {

    private long totalUsers;
    private long totalProjects;
    private long totalRewards;
}
