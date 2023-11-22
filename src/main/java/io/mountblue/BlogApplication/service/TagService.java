package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.repository.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    TagRepo tagRepo;
    @Autowired
    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }


}
