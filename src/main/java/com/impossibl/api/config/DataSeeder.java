package com.impossibl.api.config;

import com.impossibl.api.dto.PostRequest;
import com.impossibl.api.enums.PostStatus;
import com.impossibl.api.repository.PostRepository;
import com.impossibl.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

	private final PostService postService;
	private final PostRepository postRepository;

	@Override
	public void run(String... args) {
		if (postRepository.count() > 0) {
			log.info("Database already seeded ({} posts), skipping.", postRepository.count());
			return;
		}

		log.info("Seeding database with initial posts...");

		postService.create(new PostRequest(
				"How I'm making this website",
				"A short teaser for the card.",
				"""
						This blog died in 2022. Four years later, I'm finally finishing it - and writing about the process as I go. This time I'm documenting the whole thing, partly to keep myself honest and partly because the *how* turned out to be more interesting than the *what*.
						
						## Why it stalled
						
						The original build looked gorgeous but fought me at every turn. The layout was a showpiece, not a blog. Every post got squeezed into a rigid square tile, titles wrapped in ugly places, and there was never enough room for the actual writing.
						
						The deeper problem was that I designed it *visually first* without asking what the page was actually for. A blog is for reading. The square-grid was for looking at. When form and function fight like that, you lose the will to keep going.
						
						## The new stack
						
						I rebuilt on a fresh foundation:
						
						- **Angular 21** for the frontend
						- **Spring Boot** for the backend
						- A flat-retro design language I actually love
						
						The Angular jump was the steepest part. I'd last touched it at version 12, and almost everything changed - standalone components, signals, the new control flow.
						
						## What's next
						
						One step at a time. The difference this round is that the foundation is one I trust - so finishing feels like *when*, not *if*.
						""",
				"Samshi",
				PostStatus.PUBLISHED,
				Set.of("meta", "angular", "webdev"),
				true
		));

		postService.create(new PostRequest(
				"Reverse-engineering the Bambalina binding",
				"Pulling apart a structure I had no instructions for.",
				"""
						I found the Bambalina binding the way I find most things - a blurry photo on someone's feed, no tutorial, no diagram, just a finished object that shouldn't structurally work but somehow does. So I did what I always do: I made one badly to understand it.
						
						## The first failure
						
						My first attempt fell apart at the spine. I'd assumed the folds carried the tension, but they don't - the *cover* does. That's the whole trick of this structure: the case isn't decorative, it's load-bearing.
						
						## What clicked
						
						Once I stopped treating the cover as the last step and started treating it as the skeleton, everything fell into place. The text block floats inside a cover that holds it under gentle compression.
						
						## The lesson
						
						Reverse-engineering a binding teaches you more than any tutorial. A tutorial tells you *what* to do; failure tells you *why* it works. I'll take the second one every time.
						""",
				"Samshi",
				PostStatus.PUBLISHED,
				Set.of("bookbinding", "experiments"),
				false
		));

		postService.create(new PostRequest(
				"My first granny square in years",
				"Mum taught me at 18. Picking it back up.",
				"""
						My mum taught me to crochet when I was eighteen, sitting on the floor of the living room with a ball of acrylic yarn that was almost certainly the wrong weight for the hook she handed me. I made exactly one lopsided granny square and then didn't touch a hook for years.
						
						## Muscle memory is strange
						
						Picking it back up, my hands remembered more than my brain did. The chain stitch came back instantly. The double crochet took a few rows. The *tension* - keeping each stitch even - took an entire evening to stop being a fight.
						
						## Why now
						
						There's something about a granny square that's perfectly sized for a restless evening. Small enough to finish, repetitive enough to quiet your head, forgiving enough that a mistake just becomes texture.
						
						I'm not making anything in particular yet. Just squares. Sometimes the point is the doing.
						""",
				"Pooji",
				PostStatus.PUBLISHED,
				Set.of("crochet"),
				false
		));

		postService.create(new PostRequest(
				"A slow review of the Pilot Custom 74",
				"Six months of daily use, one honest verdict.",
				"""
						I don't trust reviews written after a week. A pen reveals itself slowly - how it behaves when the ink's low, how the nib settles into your hand, whether you actually *reach* for it. So here's the Pilot Custom 74 after six months of genuine daily use.
						
						## The nib
						
						The soft-fine nib is the reason to buy this pen. It has the faintest give - not a flex nib, but enough character that your writing doesn't look mechanical. After six months it's worn perfectly to my hand.
						
						## The annoyances
						
						It's not flawless. The converter holds less ink than I'd like, so I'm refilling more often than my other pens. And the clear demonstrator body, while lovely, shows every fingerprint.
						
						## The verdict
						
						Would I buy it again? Without hesitation. It's the pen I reach for when I actually want to *enjoy* writing, not just get words down. That's the highest compliment I give a pen.
						""",
				"Phani",
				PostStatus.PUBLISHED,
				Set.of("fountain pens", "reviews"),
				false
		));

		postService.create(new PostRequest(
				"Postcrossing finds from this month",
				"Cards that travelled further than I have.",
				"""
						Postcrossing is the closest thing I have to a window into strangers' lives. You send a card into the void and one comes back from somewhere you've never been, written by someone you'll never meet. This month's haul was especially good.
						
						## The standouts
						
						A hand-painted card from a retired teacher in Finland, who wrote about the light returning after the long winter. A glossy tourist card from Japan that someone had covered, every inch, in tiny handwritten recommendations for their city.
						
						## Why I keep doing it
						
						In an age of instant everything, there's something almost defiant about a postcard. It takes weeks. It might get lost. The handwriting is imperfect. And that's exactly the point - it's *slow*, and the slowness is the gift.
						
						My map of received cards has more pins than my actual travel history. The cards travel further than I do.
						""",
				"Pooji",
				PostStatus.PUBLISHED,
				Set.of("postcrossing", "collecting"),
				false
		));

		postService.create(new PostRequest(
				"Folding an origami tessellation journal",
				"When the cover is harder than the binding.",
				"""
						I wanted a journal with a cover that *moved* - something with the shifting geometry of an origami tessellation, where the folds catch light differently as you turn it. What I learned is that the cover is harder than the entire binding combined.
						
						## The folding
						
						A tessellation is dozens of precise folds that all depend on each other. Get one crease a millimetre off and the whole pattern refuses to collapse flat. I folded three full sheets before one actually worked.
						
						## Marrying it to a book
						
						The real problem was structural. A tessellated cover *wants* to flex and shift; a book *wants* a rigid spine. Reconciling those two is the whole challenge. I ended up mounting the tessellation onto a thin board backing that holds the shape without killing the play of light.
						
						## Worth it?
						
						It took four times longer than a normal cover. But it's the first journal I've made that people pick up and immediately start *touching*. Sometimes the hard cover is the whole point.
						""",
				"Samshi",
				PostStatus.PUBLISHED,
				Set.of("origami", "bookbinding", "experiments"),
				false
		));

		postService.create(new PostRequest(
				"Barre, three weeks in",
				"The soreness nobody warns you about.",
				"""
						I started barre three weeks ago expecting something gentle. Ballet-adjacent, low-impact, surely a soft introduction back into moving my body. Reader, I have never been so wrong about anything.
						
						## The shaking
						
						Nobody warns you about the *shaking*. Barre works in tiny, sustained movements - you hold a position and pulse an inch, and within thirty seconds the muscle is trembling in a way that feels like betrayal. It's humbling. I am not as strong as I assumed.
						
						## Three weeks in
						
						The soreness has shifted from "I can't sit down" to "I can feel exactly which muscle did the work." That's progress. The pulses that wrecked me in week one are merely difficult now.
						
						## Sticking with it
						
						I'm keeping at it - partly for the wedding, mostly because the shaking has started to feel like proof of something. Three weeks isn't a transformation. But it's a habit forming, and that's the part that actually matters.
						""",
				"Phani",
				PostStatus.PUBLISHED,
				Set.of("fitness"),
				false
		));

		log.info("Seeding complete: {} posts created.", postRepository.count());
	}
}