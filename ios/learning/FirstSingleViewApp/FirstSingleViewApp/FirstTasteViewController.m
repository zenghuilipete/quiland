#import "FirstTasteViewController.h"

@interface FirstTasteViewController ()
@property (weak, nonatomic) IBOutlet UITextField *firstText;

@end

@implementation FirstTasteViewController

- (NSString *) showMe{
    NSDateFormatter *dateFormatter =[[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"YYYY/MM/dd hh:mm:ssZ"];
    
    NSString *now = [dateFormatter stringFromDate:[NSDate date]];
    NSLog(@"dateString = %@",now);
    return now;
}

- (IBAction)firstButton:(id)sender {
    self.firstText.text= [
    [self.firstText.text stringByAppendingString:@". It works"] stringByAppendingString:[self showMe]
    ];
}
@end
