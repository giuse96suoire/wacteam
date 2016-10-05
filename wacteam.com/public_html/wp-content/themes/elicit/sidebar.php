<?php
/**
 * @package elicit
 */
?>
<div class="Block">

<?php if ( function_exists('dynamic_sidebar') && dynamic_sidebar('Sidebar') ) : else : ?>

<div class="Block">

<div class="item">
	    <div class="text">
			<h3><?php esc_html_e('Latest Articles', 'elicit'); ?></h3>
			<ul >
			<?php
			global $post;
			$myposts = get_posts('numberposts=5');
			foreach($myposts as $post) :
			?>
				<li><a href="<?php the_permalink(); ?>" title="<?php the_title(); ?>"><?php the_title(); ?></a><br /><?php the_time('F jS, Y') ?></li>
			<?php endforeach; ?>
			</ul>
			</div>
</div>	
<div class="item">

			<h1><?php esc_html_e('Categories', 'elicit'); ?>:</h1>
			<ul style="list-style-type: none;">
				<?php wp_list_categories('show_count=1&title_li='); ?>
			</ul>
			<div style="clear:both;"></div>
		</div>
<div class="item">
	
		<div>
			<h3><?php esc_html_e('Monthly Archive', 'elicit'); ?></h3>
			<ul >
				<?php wp_get_archives('show_post_count=1&title_li='); ?>
			</ul>
			<div style="clear:both;"></div>
		</div>
		</div>
	</div>	
		
	<?php endif; ?>
</div>
</div>