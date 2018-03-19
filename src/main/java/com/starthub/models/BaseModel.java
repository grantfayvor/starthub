package com.starthub.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Harrison on 02/03/2018.
 */

public class BaseModel {

    @Id
    @GeneratedValue
    private long id;

}
