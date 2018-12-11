package org.launchcode.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Job job = jobData.findById(id);
        model.addAttribute(job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors){

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";
        }
            int id = jobData.findById(jobForm.getEmployerId()).getId();
            //    public Job(String aName, Employer aEmployer, Location aLocation,
            //               PositionType aPositionType, CoreCompetency aSkill) {
            Employer anEmployer = jobData.getEmployers().findById(id);
            Location location = jobForm.getLocation();
            PositionType positionType = jobForm.getPositionType();
            CoreCompetency coreCompetency = jobForm.getCoreCompetency();
            Job newJob = new Job(jobForm.getName(), anEmployer, location, positionType, coreCompetency);
            jobData.add(newJob);
            model.addAttribute(newJob);

            return "redirect:?id=" +jobData.findAll().size();

    }
}
