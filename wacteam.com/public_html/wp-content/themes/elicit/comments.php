<?php
	if ('comments.php' == basename($_SERVER['SCRIPT_FILENAME']))
		die ('Please do not load this page directly. Thanks!');

	if (!empty($post->post_password)) { // if there's a password
		if ($_COOKIE['wp-postpass_' . COOKIEHASH] != $post->post_password) {  // and it doesn't match the cookie
			?>

			<p class="nocomments"><?php esc_html_e( 'This post is password protected. Enter the password to view comments.', 'elicit' ); ?></p>

			<?php
			return;
		}
	}
	
		$oddcomment = ' alt';
?>

<!-- You can start editing here. -->
	<?php if ( have_comments() ) : ?>
	<div class="post">
	<h2><?php comments_number('0', '1', '%' );?> <?php esc_html_e('Comments', 'elicit'); ?></h2>
	<ul class="commentlist">
		<?php wp_list_comments('callback=elicit_comment'); ?>
	</ul>
	<?php if ( is_singular() ) wp_enqueue_script( "comment-reply" ); ?>
	<div >
		<div class="alignleft"><?php previous_comments_link() ?></div>
		<div class="alignright"><?php next_comments_link() ?></div>
		<div class="clear"></div>
	</div>
	</div>

	<?php endif; ?>

	<?php if ('open' == $post->comment_status) : ?>
	<div class="post">

	
		<?php if ( get_option('comment_registration') && !$user_ID ) : ?>
		<p><?php esc_html_e('Bạn phải ', 'elicit'); ?> <a href="<?php echo get_option('siteurl'); ?>/wp-login.php?redirect_to=<?php echo esc_url(get_permalink()); ?>"><?php esc_html_e('đăng nhập ', 'elicit'); ?></a><?php esc_html_e('để bình luận.', 'elicit'); ?> </p>
		<?php else : ?>

		 <?php comment_form(); ?>
	</div>
		<?php endif; // If registration required and not logged in ?>
	<?php endif; // if you delete this the sky will fall on your head ?>