package value.generator.random.impl.string;

import value.generator.random.api.AbstractRandomValueGenerator;

public class RandomStringValueGenerator extends AbstractRandomValueGenerator<String> {
    @Override
    public String generateValue() {
        //get random number between 1 and 50 (inclusive)
        //generate random string with that length
        //the string should contain only letters: a-z, A-Z, 0-9, and special characters: !?,_-.() and space
        //return the string
        int length = random.nextInt(50) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int charType = random.nextInt(4);
            switch (charType) {
                case 0:
                    sb.append((char) (random.nextInt(26) + 'a'));
                    break;
                case 1:
                    sb.append((char) (random.nextInt(26) + 'A'));
                    break;
                case 2:
                    sb.append((char) (random.nextInt(10) + '0'));
                    break;
                case 3:
                    int specialChar = random.nextInt(7);
                    switch (specialChar) {
                        case 0:
                            sb.append('!');
                            break;
                        case 1:
                            sb.append('?');
                            break;
                        case 2:
                            sb.append('_');
                            break;
                        case 3:
                            sb.append('-');
                            break;
                        case 4:
                            sb.append('.');
                            break;
                        case 5:
                            sb.append('(');
                            break;
                        case 6:
                            sb.append(')');
                            break;
                        case 7:
                            sb.append(' ');
                            break;
                    }
                    break;
            }
        }
        return sb.toString();
    }
}
