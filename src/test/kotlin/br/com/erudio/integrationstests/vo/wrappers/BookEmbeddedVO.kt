package br.com.erudio.integrationstests.vo.wrappers

import br.com.erudio.integrationstests.vo.BookVO
import com.fasterxml.jackson.annotation.JsonProperty

class BookEmbeddedVO {

    @JsonProperty("bookVOList")
    var books: List<BookVO>? = null
}