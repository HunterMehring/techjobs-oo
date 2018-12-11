package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.JobField;
import org.launchcode.models.JobFieldType;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    private JobData jobData = JobData.getInstance();

    @RequestMapping(value = "")
    public String list(Model model) {
        JobFieldType[] fields = JobFieldType.values();
        model.addAttribute("fields", fields);
        return "list";
    }

    @RequestMapping(value = "values")
    public String listColumnValues(Model model, @RequestParam JobFieldType column) {

        if (column.equals(JobFieldType.ALL)) {
            return "redirect:/list/all";
        }


        ArrayList<? extends JobField> items;

        switch(column) {
            case EMPLOYER:
                items = jobData.getEmployers().findAll();
                break;
            case LOCATION:
                items = jobData.getLocations().findAll();
                break;
            case CORE_COMPETENCY:
                items = jobData.getCoreCompetencies().findAll();
                break;
            case POSITION_TYPE:
            default:
                items = jobData.getPositionTypes().findAll();
        }

        model.addAttribute("title", "All " + column.getName() + " Values");
        model.addAttribute("column", column);
        model.addAttribute("items", items);

        return "list-column";
    }

    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model,
            @RequestParam JobFieldType column, @RequestParam String name) {

        ArrayList<Job> jobs = jobData.findByColumnAndValue(column, name);

        model.addAttribute("title", "Jobs with " + column.getName() + ": " + name);
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }

    @RequestMapping(value = "all")
    public String listAllJobs(Model model) {

        ArrayList<Job> jobs = jobData.findAll();

        model.addAttribute("title", "All Jobs");
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }
}
