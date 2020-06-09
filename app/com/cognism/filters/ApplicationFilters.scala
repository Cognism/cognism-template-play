package com.cognism.filters

import javax.inject.Inject
import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter
import play.filters.gzip.GzipFilter
import play.filters.headers.SecurityHeadersFilter

class ApplicationFilters @Inject() (gzip: GzipFilter, log: LoggingFilter, cors: CORSFilter, security: SecurityHeadersFilter)
    extends DefaultHttpFilters(gzip, log, cors, security)
