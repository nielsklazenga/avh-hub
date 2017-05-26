// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = "au.org.ala" // change this to alter the default package name and Maven publishing destination

println "default_config = ${default_config?:'not set'}"
def ENV_NAME = "${appName.toUpperCase()}_CONFIG"
default_config = "/data/${appName}/config/${appName}-config.properties"
if(!grails.config.locations || !(grails.config.locations instanceof List)) {
    grails.config.locations = []
}

if(System.getenv(ENV_NAME) && new File(System.getenv(ENV_NAME)).exists()) {
    println "[${appName}] Including configuration file specified in environment: " + System.getenv(ENV_NAME);
    grails.config.locations.add "file:" + System.getenv(ENV_NAME)
} else if(System.getProperty(ENV_NAME) && new File(System.getProperty(ENV_NAME)).exists()) {
    println "[${appName}] Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
    grails.config.locations.add "file:" + System.getProperty(ENV_NAME)
} else if(new File(default_config).exists()) {
    println "[${appName}] Including default configuration file: " + default_config;
    grails.config.locations.add "file:" + default_config
} else {
    println "[${appName}] No external configuration file defined."
}

println "[${appName}] (*) grails.config.locations = ${grails.config.locations}"
println "default_config = ${default_config}"

/******************************************************************************\
 *  APP CONFIG VARS - only used if external config file not found
\******************************************************************************/
skin.layout = 'avh'
skin.orgNameLong = "Australia&apos;s Virtual Herbarium"
skin.orgNameShort = "AVH"
map.pointColour = "df4a21"
map.zoomOutsideScopedRegion = false
// Following values include NZ in default view
map.defaultLatitude = "-27.6"
map.defaultLongitude = "141.0"
map.defaultZoom = "4"

// whether crumb trail should include a home link that is external to this webabpp - ala.baseUrl is used if true
skin.includeBaseUrl = true
skin.taxaLinks.baseUrl = "http://bie.ala.org.au/species/"
skin.headerUrl = "classpath:resources/generic-header.jsp" // can be external URL
skin.footerUrl = "classpath:resources/generic-footer.jsp" // can be external URL
skin.fluidLayout = true // true or false
skin.taxaLinks.baseUrl = "http://bie.ala.org.au/species/"
skin.useAlaBie = true
defaultListView = "mapView" // mapView or listView

bie.baseUrl = "http://bie.ala.org.au/"
bie.searchPath = "/search"
bie.autocompleteHints.fq = "kingdom:Plantae"
biocache.baseUrl = "http://biocache.ala.org.au/ws"
biocache.apiKey = "api-key-to-use"
biocache.queryContext = "data_hub_uid:dh2" // data hub uid
biocache.groupedFacetsUrl = "file:///data/avh-hub/config/grouped_facets_avh.json"
spatial.baseURL = "http://spatial.ala.org.au/"
ala.baseURL = "http://www.ala.org.au"
collections.baseUrl = "http://collections.ala.org.au"
dataQualityChecksUrl = "https://docs.google.com/spreadsheet/pub?key=0AjNtzhUIIHeNdHJOYk1SYWE4dU1BMWZmb2hiTjlYQlE&single=true&gid=0&output=csv"
clubRoleForHub = "ROLE_ADMIN"

/******************************************************************************\
 *  CAS SETTINGS
 *
 *  NOTE: Some of these will be ignored if default_config exists
\******************************************************************************/

serverName = 'http://dev.ala.org.au:8080'
security.cas.appServerName = "http://dev.ala.org.au:8080"
security.cas.casServerName = 'https://auth.ala.org.au'
security.cas.uriFilterPattern = '/admin, /admin/.*'
security.cas.authenticateOnlyIfLoggedInPattern = "/occurrences/(?!.+userAssertions|facet.+).+,/explore/your-area"
security.cas.uriExclusionFilterPattern = '/images.*,/css.*,/js.*'
security.cas.loginUrl = 'https://auth.ala.org.au/cas/login'
security.cas.logoutUrl = 'https://auth.ala.org.au/cas/logout'
security.cas.casServerUrlPrefix = 'https://auth.ala.org.au/cas'
security.cas.bypass = false // not sure this is working
auth.admin_role = "ROLE_ADMIN"

speciesGroupsIgnore = "Animals,Arthropods,Bacteria,Crustaceans,Dicots,Insects,Molluscs,Protozoa"

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
 
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.serverURL = 'http://dev.ala.org.au:8080/' + appName
        serverName='http://dev.ala.org.au:8080'
        security.cas.appServerName = serverName
        security.cas.contextPath = "/${appName}"
        //grails.resources.debug = true // cache & resources plugins
    }
    test {
        grails.serverURL = 'http://biocache-test.ala.org.au'
        serverName='http://biocache-test.ala.org.au'
        security.cas.appServerName = serverName
        //security.cas.contextPath = "/${appName}"
    }
    production {
        grails.serverURL = 'http://biocache.ala.org.au'
        serverName='http://biocache.ala.org.au'
        security.cas.appServerName = serverName
    }
}

def loggingDir = (System.getProperty('catalina.base') ? System.getProperty('catalina.base') + '/logs' : './logs')
def appName = grails.util.Metadata.current.'app.name'
// log4j configuration
log4j = {
// Example of changing the log pattern for the default console
// appender:
    appenders {
        environments {
            production {
                rollingFile name: "tomcatLog", maxFileSize: '1MB', file: "${loggingDir}/${appName}.log", threshold: org.apache.log4j.Level.ERROR, layout: pattern(conversionPattern: "%d %-5p [%c{1}] %m%n")
            }
            development {
                console name: "stdout", layout: pattern(conversionPattern: "%d %-5p [%c{1}] %m%n"), threshold: org.apache.log4j.Level.DEBUG
            }
            test {
                rollingFile name: "tomcatLog", maxFileSize: '1MB', file: "/tmp/${appName}", threshold: org.apache.log4j.Level.DEBUG, layout: pattern(conversionPattern: "%d %-5p [%c{1}] %m%n")
            }
        }
    }
    root {
        // change the root logger to my tomcatLog file
        error 'tomcatLog'
        warn 'tomcatLog'
        additivity = true
    }

    error   'au.org.ala.cas.client',
            'grails.spring.BeanBuilder',
            'grails.plugin.webxml',
            'grails.plugin.cache.web.filter',
            'grails.app.services.org.grails.plugin.resource',
            'grails.app.taglib.org.grails.plugin.resource',
            'grails.app.resourceMappers.org.grails.plugin.resource'

    debug   'grails.app','au.org.ala.cas','au.org.ala.biocache'
}
