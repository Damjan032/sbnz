package sbnz.advertisingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Advertisement {

    private String title;
    private String content;
    private String targetUrl;
    private List<String> tags;
    private double budget;
    private Advertiser advertiser;
}
