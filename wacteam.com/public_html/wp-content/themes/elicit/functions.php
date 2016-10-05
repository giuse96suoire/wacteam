<?php
/**
 * elicit functions and definitions
 *
 * @package elicit
 */
if ( ! isset( $content_width ) ) {
	$content_width = 720;
}

function elicit_content_width() {
  if ( is_page_template( 'full-width.php' ) ) {
    global $content_width;
    $content_width = 1008; 
  }
}
add_action( 'template_redirect', 'elicit_content_width' );

function elicit_widgets_init() {
	
	register_sidebar(array(
		'name'=>  esc_html__('sidebar', 'elicit' ),
        'id'=> 'sidebar-1',
		'before_widget' => '<aside id="%1$s" class="item %2$s">',
		'after_widget' => '</aside>',
		'before_title' => '<h3 class="widget-title"><span class="widget-header-title">',
		'after_title' => '</span></h3>',

	));

	register_sidebar( array(
		'name'          => esc_html__('Header Right','elicit'),
		'id'            => 'header_right',
		'before_widget' => '<div>',
		'after_widget'  => '</div>',
		'before_title'  => '<h2>',
		'after_title'   => '</h2>',
	) );
	register_sidebar( array(
		'name'          => esc_html__('Footer 1','elicit'),
		'id'            => 'footer-1',
		'before_widget' => '<div>',
		'after_widget'  => '</div>',
		'before_title'  => '<h2>',
		'after_title'   => '</h2>',
	) );
	register_sidebar( array(
		'name'          => esc_html__('Footer 2','elicit'),
		'id'            => 'footer-2',
		'before_widget' => '<div>',
		'after_widget'  => '</div>',
		'before_title'  => '<h2>',
		'after_title'   => '</h2>',
	) );
	register_sidebar( array(
		'name'          => esc_html__('Footer 3','elicit'),
		'id'            => 'footer-3',
		'before_widget' => '<div>',
		'after_widget'  => '</div>',
		'before_title'  => '<h2>',
		'after_title'   => '</h2>',
	) );

	  register_widget('Elicit_Social_Profile');

	}
	
add_action( 'widgets_init', 'elicit_widgets_init' );
require_once(get_template_directory() . '/includes/widgets/popular-posts.php');
require_once(get_template_directory() . '/includes/widgets/widget-social.php');
require_once(get_template_directory() . '/includes/customizer.php');
require_once(get_template_directory() . '/includes/customizer-styles.php');

add_action('wp_head', 'elicit_custom_css');
function elicit_custom_css() {
	if( !empty( $blogology_internal_css ) ) {
		echo '<!-- '.get_bloginfo('name').' Internal Styles -->';
		?><style type="text/css"><?php echo $blogology_internal_css; ?></style><?php
	}

	$elicit_custom_css = get_theme_mod( 'elicit_custom_css', '' );
	if( !empty( $elicit_custom_css ) ) {
		echo '<!-- '.get_bloginfo('name').' Custom Styles -->';
		?><style type="text/css"><?php echo $elicit_custom_css; ?></style><?php
	}
}
	
add_action( 'after_setup_theme', 'elicit_setup' );
function elicit_setup() {
	
/*
* Translations can be filed in the /languages/ directory.
*/
load_theme_textdomain( 'elicit', get_template_directory() . '/languages' );
  
add_theme_support( "title-tag" );
add_theme_support( 'automatic-feed-links' );
add_theme_support( 'html5', array( 'comment-list', 'comment-form', 'search-form', 'gallery', 'caption' ) );


add_theme_support( 'custom-background', apply_filters( 'elecit_custom_background_args', array(
	'default-attachment' => 'fixed',
	'default-image' => '',
) ) );


add_theme_support( 'post-thumbnails' );
add_image_size('postthumbnail', 700, 340, true );
add_image_size( 'elicit-small', 100, 47, true);
add_theme_support( 'custom-header', array(
	'width'       => 1280,
	'height'      => 150,
) );

}	
add_action( 'init', 'elicit_register_my_menu' );
add_action( 'wp_enqueue_scripts', 'elicit_scripts_styles' );
function elicit_scripts_styles() {
	wp_enqueue_script( 'elicit-scrol', get_template_directory_uri() . '/js/scroll-to-top.js', array('jquery') );
	wp_enqueue_style('bootstrap-css', get_stylesheet_directory_uri() . '/css/bootstrap.min.css');
	wp_enqueue_style( 'elicit-style', get_stylesheet_uri());
	wp_enqueue_script('elicit-bootstrap-js', get_template_directory_uri().'/js/bootstrap.min.js', array('jquery') );
	wp_enqueue_style('font-awesome', get_template_directory_uri() .'/fontawesome/css/font-awesome.min.css');
	wp_enqueue_style( 'dashicons' );
	
}

function elicit_comment($comment, $args, $depth) {
	
   $GLOBALS['comment'] = $comment; ?>

   <li <?php comment_class(); ?> id="li-comment-<?php comment_ID() ?>">
     <div class="comment<?php echo (!empty($oddcomment) ? $oddcomment:""); ?>" id="comment-<?php comment_ID() ?>"  >
		<a class="gravatar"><?php echo get_avatar($comment,$size='40'); ?></a>

		<div class="comment-meta">
			<span><?php comment_author_link() ?></span> (<a href="#comment-<?php comment_ID() ?>" title="">#</a>) 
			<?php if ($comment->comment_approved == '0') : ?>
			<p><strong><?php esc_html_e('Your comment is waiting for approval.', 'elicit'); ?></strong></p>
			<?php endif; ?>
			<br/>
			<small style="line-height:2.3;"><?php comment_date('F jS, Y') ?></small>
			<br/>
		</div>
		<?php comment_text() ?>			
		<div class="clear"></div>

		<div class="reply">
		<?php comment_reply_link(array_merge( $args, array('depth' => $depth, 'max_depth' => $args['max_depth']))) ?>
		</div>
     </div>

<?php

}
function new_excerpt_more($more) {
return '&hellip;';
}
add_filter('excerpt_more', 'new_excerpt_more');	

add_filter( 'excerpt_length', 'elicit_excerpt_length' );
function elicit_excerpt_length( $length ) {
	return get_theme_mod( 'excerpt_length', 55 );
}


function elicit_excerpt_length_count($string, $word_limit)
{
	$words = explode(' ', $string, ($word_limit + 1));
	
	if(count($words) > $word_limit) {
		array_pop($words);
	}
	
	return implode(' ', $words);
}
add_action( 'after_setup_theme', 'elicit_bootstrap_setup' );
 
if ( ! function_exists( 'elicit_bootstrap_setup' ) ):
 
        function elicit_bootstrap_setup(){
 
                       


                    function elicit_register_my_menu() {
                register_nav_menu('header-menu',__( 'Header Menu','elicit' ));
                 }
                class Bootstrap_Walker_Nav_Menu extends Walker_Nav_Menu {
 
                       
                function start_lvl( &$output, $depth = 0, $args = array() ) {
		$indent = str_repeat("\t", $depth);
		$output .= "\n$indent<ul class=\" dropdown-menu\">\n";
                        }
 
                        function start_el( &$output, $item, $depth = 0, $args = array(), $id = 0 ) {
                               
                                $indent = ( $depth ) ? str_repeat( "\t", $depth ) : '';
 
                                $li_attributes = '';
                                $class_names = $value = '';
 
                                $classes = empty( $item->classes ) ? array() : (array) $item->classes;
                                $classes[] = ($args->has_children) ? 'dropdown' : '';
                                $classes[] = ($item->current || $item->current_item_ancestor) ? 'active' : '';
                                $classes[] = 'menu-item-' . $item->ID;
 
 
                                $class_names = join( ' ', apply_filters( 'nav_menu_css_class', array_filter( $classes ), $item, $args ) );
                                $class_names = ' class="' . esc_attr( $class_names ) . '"';
 
                                $id = apply_filters( 'nav_menu_item_id', 'menu-item-'. $item->ID, $item, $args );
                                $id = strlen( $id ) ? ' id="' . esc_attr( $id ) . '"' : '';
 
                                $output .= $indent . '<li' . $id . $value . $class_names . $li_attributes . '>';
 
                                $attributes  = ! empty( $item->attr_title ) ? ' title="'  . esc_attr( $item->attr_title ) .'"' : '';
                                $attributes .= ! empty( $item->target )     ? ' target="' . esc_attr( $item->target     ) .'"' : '';
                                $attributes .= ! empty( $item->xfn )        ? ' rel="'    . esc_attr( $item->xfn        ) .'"' : '';
                                $attributes .= ! empty( $item->url )        ? ' href="'   . esc_attr( $item->url        ) .'"' : '';
                                $attributes .= ($args->has_children)        ? ' class="dropdown-toggle" data-toggle="dropdown"' : '';
 
                                $item_output = $args->before;
                                $item_output .= '<a'. $attributes .'>';
                                $item_output .= $args->link_before . apply_filters( 'the_title', $item->title, $item->ID ) . $args->link_after;
                                $item_output .= ($args->has_children) ? ' <b class="caret"></b></a>' : '</a>';
                                $item_output .= $args->after;
 
                                $output .= apply_filters( 'walker_nav_menu_start_el', $item_output, $item, $depth, $args );
                        }
 
                        function display_element( $element, &$children_elements, $max_depth, $depth=0, $args, &$output ) {
                               
                                if ( !$element )
                                        return;
                               
                                $id_field = $this->db_fields['id'];
 
                                //display this element
                                if ( is_array( $args[0] ) )
                                        $args[0]['has_children'] = ! empty( $children_elements[$element->$id_field] );
                                else if ( is_object( $args[0] ) )
                                        $args[0]->has_children = ! empty( $children_elements[$element->$id_field] );
                                $cb_args = array_merge( array(&$output, $element, $depth), $args);
                                call_user_func_array(array(&$this, 'start_el'), $cb_args);
 
                                $id = $element->$id_field;
 
                                // descend only when the depth is right and there are childrens for this element
                                if ( ($max_depth == 0 || $max_depth > $depth+1 ) && isset( $children_elements[$id]) ) {
 
                                        foreach( $children_elements[ $id ] as $child ){
 
                                                if ( !isset($newlevel) ) {
                                                        $newlevel = true;
                                                        //start the child delimiter
                                                        $cb_args = array_merge( array(&$output, $depth), $args);
                                                        call_user_func_array(array(&$this, 'start_lvl'), $cb_args);
                                                }
                                                $this->display_element( $child, $children_elements, $max_depth, $depth + 1, $args, $output );
                                        }
                                                unset( $children_elements[ $id ] );
                                }
 
                                if ( isset($newlevel) && $newlevel ){
                                        //end the child delimiter
                                        $cb_args = array_merge( array(&$output, $depth), $args);
                                        call_user_func_array(array(&$this, 'end_lvl'), $cb_args);
                                }
 
                                //end this element
                                $cb_args = array_merge( array(&$output, $element, $depth), $args);
                                call_user_func_array(array(&$this, 'end_el'), $cb_args);
                               
                        }
                       
                }
 
        }endif;
?>