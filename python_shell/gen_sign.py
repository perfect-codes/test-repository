from hashlib import sha256, md5
import hmac
import time


def make_sign(api_name, consumer_key, consumer_secret, nonce):
    """validate sign
    1: sort data
    2: generate sign string: api_name&{data key}={data value}&...
    3: hmac use consumer key with sha256
    """
    data = {
        'consumer_key': consumer_key,
        'nonce': nonce,
        'timestamp': nonce,
    }
    sorted_keys = sorted(data.keys())
    msg_list = [api_name]
    for key in sorted_keys:
        msg_list.append(u'{}={}'.format(key, data[key]))
    msg = '&'.join(msg_list)
    return hmac.new(consumer_secret, msg, sha256).hexdigest()


if __name__=="__main__":

    api_name = "/biz.traffic/fudai/send"
    # api_name = "/biz.traffic/fudai/precheck"
    
    consumer_key='0392428941'
    consumer_secret = '1ee0767a88e6ca71a2c1f4f376ceac43675b33e2'

    nonce = int(time.time())
    print nonce
    print make_sign(api_name, consumer_key, consumer_secret, nonce)
