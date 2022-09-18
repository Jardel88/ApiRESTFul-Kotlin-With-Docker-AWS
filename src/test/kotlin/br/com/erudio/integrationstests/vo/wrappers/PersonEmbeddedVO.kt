package br.com.erudio.integrationstests.vo.wrappers

import br.com.erudio.integrationstests.vo.PersonVO
import com.fasterxml.jackson.annotation.JsonProperty

class PersonEmbeddedVO {

    @JsonProperty("personVOList")
    var persons: List<PersonVO>? = null
}