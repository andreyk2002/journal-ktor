package com.jetbrains.handson.website

import com.jetbrains.handson.website.entity.JournalEntry
import com.jetbrains.handson.website.entity.inMemoryEntries
import com.jetbrains.handson.website.entity.inMemoryUsers
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Authentication) {
        basic("mocked") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == "admin" && credentials.password == "admin") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
        basic("users") {
            validate {
                credentials ->
                val find = inMemoryUsers.find { user ->
                    user.name == credentials.name && user.password == credentials.password
                }
                if(find != null){
                    UserIdPrincipal(credentials.name)
                } else{
                    null
                }
            }
        }
    }

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        authenticate("users") {
            static("/static") {
                resources("files")
            }
            get("/") {
                call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to inMemoryEntries), ""))
            }
            post("/submit") {
                val receiveParameters = call.receiveParameters()
                val headline = receiveParameters["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                val description =
                    receiveParameters["description"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                val journalEntry = JournalEntry(headline, description)
                inMemoryEntries.add(journalEntry)
                return@post call.respondRedirect("/")
            }
        }
    }
}
