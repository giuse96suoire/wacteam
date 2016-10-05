<?php

add_filter('wp_list_categories', 'elicit_category_count');
function elicit_category_count($links) {
	$links = str_replace('</a> (', '</a><span class="category-count">', $links);
	$links = str_replace(')', '</span>', $links);
	return $links;
}