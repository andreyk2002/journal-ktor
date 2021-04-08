package com.jetbrains.handson.website

import com.jetbrains.handson.website.entity.JournalEntry
import com.jetbrains.handson.website.entity.inMemoryEntries
import freemarker.cache.*
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        static("/static") {
            resources("files")
        }
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to inMemoryEntries), ""))
        }
        post("/submit") {
            val receiveParameters = call.receiveParameters()
            val headline = receiveParameters["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val description = receiveParameters["description"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val journalEntry = JournalEntry(headline, description)
            inMemoryEntries.add(journalEntry)
            return@post call.respondRedirect("/")
        }
    }
}
