package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.JobFieldType;
import org.launchcode.models.forms.SearchForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    private JobData jobData = JobData.getInstance();

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute(new SearchForm());
        return "search";
    }

    @RequestMapping(value = "results")
    public String search(Model model,
                         @ModelAttribute SearchForm searchForm) {

        ArrayList<Job> jobs;

        if (searchForm.getSearchField().equals(JobFieldType.ALL)) {
            jobs = jobData.findByValue(searchForm.getKeyword());
        } else {
            jobs = jobData.findByColumnAndValue(searchForm.getSearchField(), searchForm.getKeyword());
        }

        model.addAttribute("jobs", jobs);

        return "search";
    }

}
