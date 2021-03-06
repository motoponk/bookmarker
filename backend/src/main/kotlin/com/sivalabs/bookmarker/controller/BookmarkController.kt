package com.sivalabs.bookmarker.controller

import com.sivalabs.bookmarker.entity.Bookmark
import com.sivalabs.bookmarker.model.BookmarkDTO
import com.sivalabs.bookmarker.model.BookmarkFilterType
import com.sivalabs.bookmarker.security.SecurityUtils
import com.sivalabs.bookmarker.service.BookmarkService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/bookmarks")
@PreAuthorize("isAuthenticated()")
class BookmarkController(
    private val securityUtils: SecurityUtils,
    private val bookmarkService: BookmarkService
) {

    @GetMapping
    fun allUserBookmarks(@RequestParam(name = "filter", defaultValue = "") filter: String): List<BookmarkDTO> {
        return bookmarkService.getUserBookmarks(securityUtils.loginUser()!!.id,
                BookmarkFilterType.fromString(filter))
    }

    @GetMapping("/{id}")
    fun getBookmarkById(@PathVariable id: Long): ResponseEntity<BookmarkDTO> {
        return bookmarkService.getBookmarkById(id)?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBookmark(@RequestBody bookmark: Bookmark) {
        bookmark.createdBy = securityUtils.loginUser()!!
        bookmarkService.createBookmark(bookmark)
    }

    @DeleteMapping("/{id}")
    fun deleteBookmark(@PathVariable id: Long) {
        bookmarkService.deleteBookmark(id)
    }
}
