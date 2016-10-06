<?php
/**
 * @package elicit
 */
get_header(); ?>
 <div class="col-sm-8 blog-main-<?php the_ID(); ?>" <?php post_class(); ?>>

	<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
	<?php
	$prev_link = get_previous_post_link('%link &raquo; ');
	$next_link = get_next_post_link(' &laquo; %link');
	?>
	<?php if ($prev_link || $next_link): ?>

	<?php endif; ?>

<div class="blog-post">
		
		<?php if(has_post_thumbnail()) : ?>
		<div>
			<a href="<?php echo get_permalink() ?>"><?php the_post_thumbnail('postthumbnail'); ?></a>
		</div>
		<?php endif; ?>
		<div class="cleared"></div>	
		<h2 class="blog-post-title">
		<span ><a href="<?php the_permalink() ?>" rel="bookmark" title="<?php printf(__('Permanent Link to %s', 'elicit'), the_title_attribute('echo=0')); ?>">
		<?php the_title(); ?>
		</a></span>
		</h2>	
		<div class="metadata">
			<?php $icons = array(); ?>
			<?php if (!is_page()): ?><span class="thetime updated"><i class="fa fa-calendar"></i><?php the_time(__(' F jS, Y', 'elicit')) ?></span>
			<?php endif; ?><?php if (!is_page()): ?><span class="theauthor"><i class="fa fa-user"></i> <a href='<?php echo esc_url( get_author_posts_url( get_the_author_meta( 'ID' ) ) ); ?>'><?php the_author() ?></a></span>
			<?php endif; ?><?php if (current_user_can('edit_post', $post->ID)): ?><span class="post-edit"><i class="fa fa-pencil-square-o"></i><?php edit_post_link(__(' Edit', 'elicit')) ?></span>		
			<?php endif; ?><?php if (!is_page()): ?><span class="thecategory"><i class="fa fa-folder-open"></i><?php printf(__(' %s', 'elicit'), get_the_category_list(', ')); ?></span>
			<?php endif; ?><?php if (!is_page() && !is_single()): ?><span class="thecomment"><i class="fa fa-comments"></i><?php comments_popup_link(__(' No Comments', 'elicit'), __(' 1 Comment', 'elicit'), __('% Comments', 'elicit'), '', __(' Comments Closed', 'elicit') ); ?></span>
			<?php endif; ?>
		</div><br>
		<div >
			<?php if (is_search()) the_excerpt(); else the_content( '' ); ?>
		</div>
		
		<div class="post-tag">
		<?php the_tags(__('Tags', 'elicit') . ' ', ' ', ' '); ?>
		</div>
		<div class="cleared"></div>
		
	</div>

	<?php wp_link_pages( ); ?>

	<?php if ($prev_link || $next_link): ?>

	<div class="navigation" >
		<div   class="singleright">
		<p><?php esc_html_e('Bài tiếp theo', 'elicit'); ?></p>
			<div><?php echo $prev_link; ?></div>
		</div>

		<div class="singleleft ">
		<p><?php esc_html_e('Bài trước', 'elicit'); ?></p>
			<div><?php echo $next_link; ?></div>
		</div>
	</div>

	<?php endif; ?>

	<div class="cleared"></div>

	<div class="cleared"></div>

	<?php comments_template(); ?>
	<?php endwhile; ?>
	<?php else: ?>
	<p><?php esc_html_e('Sorry, no posts matched your criteria.', 'elicit'); ?></p>
	<?php endif; ?>
	<div class="cleared"></div>
	
</div>
<div class="col-sm-4">
	<div class="cleared"></div>
	<?php get_template_part( 'sidebar' ); ?>
	
</div>



<?php get_footer(); ?>